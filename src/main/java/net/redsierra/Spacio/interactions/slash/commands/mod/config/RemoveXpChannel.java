package net.redsierra.Spacio.interactions.slash.commands.mod.config;

import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.util.Objects;

public class RemoveXpChannel extends Command {

    public RemoveXpChannel() {
        setName("removexpchannel");
        setDescription("Remove an XP channel.");
        setCategory("mod");
    }
    @Override
    public void execute(SlashCommandInteraction commandEvent) {
        SlashCommandInteractionEvent event = commandEvent.getEvent();
        BotConfig config = new BotConfig();
        MongoDatabase db = config.getDatabase();
        Channel ch = Objects.requireNonNull(event.getOption("channel")).getAsChannel();

        if (db.getCollection("xpChannels").find(new Document("channelId", ch.getId())).first() == null) {
            event.reply("This channel is not an XP channel.").queue();
        } else {
            db.getCollection("xpChannels").deleteOne(new Document("channelId", ch.getId()));
            event.reply("The channel has been removed from the XP channels list.").queue();
        }
    }
}
