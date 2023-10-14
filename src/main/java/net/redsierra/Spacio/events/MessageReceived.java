package net.redsierra.Spacio.events;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.database.Database;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MessageReceived extends ListenerAdapter {

    private static final int COOLDOWN_PERIOD = 60;
    private final Map<String, Instant> xpCooldown = new HashMap<>();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Spacio bot = new Spacio();
        Logger logger = bot.logger;
        BotConfig config;
        try {
            config = new BotConfig();
        } catch (IOException  | ParseException e) {
            throw new RuntimeException(e);
        }

        if(!event.isFromGuild()) return;


        if(event.getAuthor().isBot()) return;

        Database db;
        try {
            db = new Database();
        } catch (IOException | ParseException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        MongoCollection<Document> collection = db.getDatabase().getCollection("users");
        String userId = event.getAuthor().getId();

        if(collection.find(new Document("userId", userId)).first() == null) {

            Document doc = new Document("userId", userId)
                    .append("xp", 0)
                    .append("level", 0);
            collection.insertOne(doc);

            xpCooldown.put(userId, Instant.now().plusSeconds(COOLDOWN_PERIOD));

        } else {
            List<String> xpChannels = config.getXPChannels();


            if(!xpChannels.contains(event.getChannel().getId())) return;

            if(xpCooldown.containsKey(userId)) {
                Instant lastTime = xpCooldown.get(userId);
                if(lastTime.plusSeconds(COOLDOWN_PERIOD).isAfter(Instant.now())) return;
            }

            xpCooldown.put(userId, Instant.now().plusSeconds(COOLDOWN_PERIOD));
            Document user = collection.find(new Document("userId", userId)).first();

            assert user != null;

            int currentLevel = user.getInteger("level");
            int currentXP = user.getInteger("xp");
            int requiredXP = (currentLevel + 1) * 350;
            int xp = new Random().nextInt(25, 30) + 1;
            int axp = currentXP + (xp);

            user.append("xp", axp);
            user.append("level", currentLevel);
            collection.replaceOne(new Document("userId", userId), user);

            if (currentXP >= requiredXP) {
                user.append("level", currentLevel + 1);
                user.append("xp", 0);
                collection.replaceOne(new Document("userId", userId), user);
                event.getChannel().sendMessage("WOW "+event.getAuthor().getAsMention()+"! You have leveled up to level " +
                        (currentLevel + 1) + "!").queue();
                logger.info("User " + event.getAuthor().getGlobalName() + " has leveled up to level " + (currentLevel + 1) + "!");
            }


        }
    }
}
