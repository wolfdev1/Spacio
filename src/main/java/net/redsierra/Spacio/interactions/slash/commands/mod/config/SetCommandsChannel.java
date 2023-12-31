package net.redsierra.Spacio.interactions.slash.commands.mod.config;

import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.util.Objects;

public class SetCommandsChannel extends Command {

    public SetCommandsChannel() {
        super.setName("setcommandschannel");
        super.setCategory("mod");
        super.setDescription("Set the channel where the bot will send the commands.");
    }
    @Override
    public void execute(SlashCommandInteraction ev) {
        BotConfig config = new BotConfig();

        SlashCommandInteractionEvent event = ev.getEvent();
        Channel ch = Objects.requireNonNull(event.getOption("channel")).getAsChannel();
        MongoDatabase db = config.getDatabase();
        if (config.getCommandsChannelId() != null) {
            db.getCollection("botchannels").updateOne(new Document("name", "commands"), new Document("$set", new Document("id", ch.getId())));
        } else {
            db.getCollection("botchannels").insertOne(new Document("name", "commands").append("id", ch.getId()));
        }
        event.reply("The commands channel has been set to " + ch.getAsMention()).setEphemeral(true).queue();

    }
}
