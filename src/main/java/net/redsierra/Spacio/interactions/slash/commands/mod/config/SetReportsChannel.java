package net.redsierra.Spacio.interactions.slash.commands.mod.config;

import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.util.Objects;

public class SetReportsChannel extends Command {

    public SetReportsChannel() {
        setName("setreportschannel");
        setDescription("Set the channel where the bot will send the reports.");
        setCategory("mod");
    }
    @Override
    public void execute(SlashCommandInteraction ev) {
        BotConfig config = new BotConfig();

        SlashCommandInteractionEvent event = ev.getEvent();
        Channel ch = Objects.requireNonNull(event.getOption("channel")).getAsChannel();
        MongoDatabase db = config.getDatabase();
        if (config.getReportsChannel() != null) {
            db.getCollection("botchannels").deleteOne(new Document("name", "reports"));
            db.getCollection("botchannels").insertOne(new Document("name", "reports").append("id", ch.getId()));
        } else {
            db.getCollection("botchannels").insertOne(new Document("name", "reports").append("id", ch.getId()));
        }
        event.reply("The reports logs channel has been set to " + ch.getAsMention()).setEphemeral(true).queue();

    }
}
