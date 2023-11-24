package net.redsierra.Spacio.interactions.slash.commands.mod.config;

import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.util.Objects;


public class AddXpChannel extends Command {

    public AddXpChannel() {
        super.setName("addxpchannel");
        super.setCategory("mod");
        super.setDescription("Adds a channel to the xp system.");
    }
    @Override
    public void execute(SlashCommandInteraction commandEvent) {
        BotConfig config = new BotConfig();
        MongoDatabase db = config.getDatabase();
        SlashCommandInteractionEvent event = commandEvent.getEvent();
        Channel channel = Objects.requireNonNull(event.getOption("channel")).getAsChannel();
        Document doc = new Document("id", channel.getId());
        if (db.getCollection("xpchannels").find(doc).first() != null) {
            event.reply("This channel is already in the xp system.").setEphemeral(true).queue();
        }
        db.getCollection("xpchannels").insertOne(new Document("id", channel.getId()));
        event.reply("The channel " + channel.getAsMention() + " has been added to the xp system.").setEphemeral(true).queue();



    }
}
