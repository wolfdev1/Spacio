package net.redsierra.Spacio.interactions.slash.commands.mod.config;

import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.util.Objects;

public class SetWelcomeChannel extends Command {

    public SetWelcomeChannel() {
        super.setName("setwelcomechannel");
        super.setDescription("Set the welcome channel.");
        super.setCategory("mod");
    }
    @Override
    public void execute(SlashCommandInteraction ev) {
        BotConfig config = new BotConfig();

        SlashCommandInteractionEvent event = ev.getEvent();
        Channel ch = Objects.requireNonNull(event.getOption("channel")).getAsChannel();
        MongoDatabase db = config.getDatabase();
        if (config.getWelcomeChannelId() != null) {
            db.getCollection("botchannels").updateOne(new Document("name", "welcome"), new Document("$set", new Document("id", ch.getId())));
        } else {
            db.getCollection("botchannels").insertOne(new Document("name", "welcome").append("id", ch.getId()));
        }
        event.reply("The welcome channel has been set to " + ch.getAsMention()).setEphemeral(true).queue();

    }
}
