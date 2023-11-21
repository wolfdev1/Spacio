package net.redsierra.Spacio.events;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import java.time.Instant;
import java.util.*;

public class MessageReceived extends ListenerAdapter {

    private static final int COOLDOWN_PERIOD = 60;
    private final Map<String, Instant> xpCooldown = new HashMap<>();


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Spacio bot = new Spacio();
        Logger logger = bot.logger;
        BotConfig config;
        config = new BotConfig();

        if(!event.isFromGuild()) return;


        if(event.getAuthor().isBot()) return;

        MongoDatabase db = new BotConfig().getDatabase();
        MongoCollection<Document> collection = db.getCollection("users");
        String userId = event.getAuthor().getId();

        if(collection.find(new Document("userId", userId)).first() == null) {

            Document doc = new Document("userId", userId)
                    .append("xp", 0)
                    .append("level", 0)
                    .append("avatar_url", 0)
                    .append("name", 0);
            collection.insertOne(doc);

            xpCooldown.put(userId, Instant.now().plusSeconds(COOLDOWN_PERIOD));

        } else {
            MongoCollection<Document> xpChannels = config.getXPChannels();
            Document c = xpChannels.find(new Document("id", event.getChannel().getId())).first();



            if(c == null) return;

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
            user.append("avatar_url", event.getAuthor().getAvatarUrl());
            user.append("name", event.getAuthor().getName());
            user.append("level", currentLevel);
            collection.replaceOne(new Document("userId", userId), user);

            if (currentXP >= requiredXP) {
                user.append("avatar_url", event.getAuthor().getAvatarUrl());
                user.append("name", event.getAuthor().getName());
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
