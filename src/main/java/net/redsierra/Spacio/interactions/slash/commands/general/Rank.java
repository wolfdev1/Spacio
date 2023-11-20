package net.redsierra.Spacio.interactions.slash.commands.general;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.utils.FileUpload;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import net.redsierra.Spacio.util.RankImageGen;
import org.bson.Document;
import java.io.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class Rank extends Command {


    public Rank() {
        super.setName("rank");
        super.setCategory("info");
        super.setDescription("Return a card with your level and experience if you have a level greater than zero, otherwise it will return an error.");

    }

    @Override
    public void execute(SlashCommandInteraction ev) {
        SlashCommandInteractionEvent event = ev.getEvent();
        MongoDatabase db = null;
        try {
            db = new BotConfig().getDatabase();
            MongoCollection<Document> c = db.getCollection("users");
            String userId = event.getUser().getId();

            Document userDoc = c.find(new Document("userId", userId)).first();
            if (userDoc == null) {
                event.reply("Sorry... You have not sent any messages yet! Please try again later").queue();
            } else {
                int level = userDoc.getInteger("level");
                int xp = userDoc.getInteger("xp");
                RankImageGen gen = new RankImageGen();

                try (InputStream f = gen.getImage(event.getUser().getName(), event.getUser().getAvatarUrl(), xp, level)) {
                    event.deferReply(true).queue();
                    InteractionHook hook = event.getHook();
                    hook.setEphemeral(true);
                    hook.sendMessage("Generating your rank card...").queue();

                    hook.editOriginal("Here is your rank card").setFiles(FileUpload.fromData(f, "rank.png")).delay(8, TimeUnit.SECONDS).queue();
                } catch (Exception e) {
                    event.reply("Sorry... Something went wrong! Please try again later").queue();
                }
            }
        } catch (Exception e) {
            event.reply("Sorry... Something went wrong! Please try again later").queue();
        }
    }

}
