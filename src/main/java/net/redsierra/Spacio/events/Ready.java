package net.redsierra.Spacio.events;


import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.database.Database;
import net.redsierra.Spacio.interactions.slash.commands.general.*;
import net.redsierra.Spacio.interactions.slash.commands.mod.*;
import net.redsierra.Spacio.interactions.slash.commands.mod.config.*;
import net.redsierra.Spacio.interactions.slash.commands.music.*;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Ready extends ListenerAdapter {

    TextChannel channel;

    public Ready() {
        Database database = new Database(new BotConfig());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        BotConfig config = new BotConfig();

        Guild guild = event.getJDA().getGuildById(config.getDefaultGuildId());
        assert guild != null;

        MongoDatabase db = config.getDatabase();
        List<TextChannel> textChannels = guild.getTextChannels();

        for (TextChannel channel : textChannels) {
            String channelId = channel.getId();
            Document channelDoc = db.getCollection("guildchannels").find(new Document("id", channelId)).first();
            boolean parent = channel.getParentCategory() == null;

            boolean p = guild.getPublicRole().getPermissions().contains(Permission.VIEW_CHANNEL);
            Document doc = new Document("id", channelId)
                    .append("name", channel.getName())
                    .append("private", p)
                    .append("nsfw", channel.isNSFW())
                    .append("slowmode", channel.getSlowmode())
                    .append("topic", (channel.getTopic() == null ? "null" : channel.getTopic()))
                    .append("position", channel.getPosition())
                    .append("parent_id", (parent ? "null" : channel.getParentCategory().getId()))
                    .append("parent_name", (parent ? "null" : channel.getParentCategory().getName()))
                    .append("type", channel.getType().toString())
                    .append("time_created", String.valueOf(channel.getTimeCreated().toInstant().toEpochMilli()));

            if (channelDoc == null) {
                db.getCollection("guildchannels").insertOne(doc);
            } else if (!channelDoc.isEmpty()) {
                db.getCollection("guildchannels").replaceOne(new Document("id", channelId), doc);
            }
        }

        if (config.getCommandsChannelId() == null) {
            guild.getTextChannels().get(0).sendMessage("The commands channel has not been set. Please set it using `/setcommandschannel`.").queue();
        } else {
            channel = guild.getTextChannelById(config.getCommandsChannelId());
            assert channel != null;
        }

             SlashCommandHandler.registerCommand(new SetCommandsChannel().getName(), new SetCommandsChannel());

        try {
             SlashCommandHandler.registerCommand(new Rank().getName(), new Rank());
             SlashCommandHandler.registerCommand(new Warn().getName(), new Warn());
             SlashCommandHandler.registerCommand(new ClearWarn().getName(), new ClearWarn());
             SlashCommandHandler.registerCommand(new Warnings().getName(), new Warnings());
             SlashCommandHandler.registerCommand(new ForceSkip().getName(), new ForceSkip());
             SlashCommandHandler.registerCommand(new Skip().getName(), new Skip());
             SlashCommandHandler.registerCommand(new Queue().getName(), new Queue());
             SlashCommandHandler.registerCommand(new EditTicket().getName(), new EditTicket());
             SlashCommandHandler.registerCommand(new Roles().getName(), new Roles());
             SlashCommandHandler.registerCommand(new Play().getName(), new Play());
             SlashCommandHandler.registerCommand(new Join().getName(), new Join());
             SlashCommandHandler.registerCommand(new BotInfo().getName(), new BotInfo());
             SlashCommandHandler.registerCommand(new Avatar().getName(), new Avatar());
             SlashCommandHandler.registerCommand(new Report().getName(), new Report());
             SlashCommandHandler.registerCommand(new Help().getName(), new Help());
             SlashCommandHandler.registerCommand(new Mute().getName(), new Mute());
             SlashCommandHandler.registerCommand(new WhoIs().getName(), new WhoIs());
             SlashCommandHandler.registerCommand(new UnMute().getName(), new UnMute());
             SlashCommandHandler.registerCommand(new Fact().getName(), new Fact());
             SlashCommandHandler.registerCommand(new AddXpChannel().getName(), new AddXpChannel());
             SlashCommandHandler.registerCommand(new RemoveXpChannel().getName(), new RemoveXpChannel());
             SlashCommandHandler.registerCommand(new SetReportsChannel().getName(), new SetReportsChannel());
             SlashCommandHandler.registerCommand(new SetWelcomeChannel().getName(), new SetWelcomeChannel());
             SlashCommandHandler.registerCommand(new Kick().getName(), new Kick());
             SlashCommandHandler.registerCommand(new Ticket().getName(), new Ticket());

             new Spacio().logger.info("Successfully registered " + SlashCommandHandler.getCommands().size() + " commands.");


             URL url = this.getClass().getClassLoader().getResource("config.json");
             assert url != null;
             URLConnection urlConnection = url.openConnection();
             if (urlConnection instanceof JarURLConnection) {
                 channel.sendMessage("**Spacio** has been deployed to production using version **" + config.getProjectVersion() + ".**").queue();
             } else {
                 channel.sendMessage("**Spacio** has been deployed to development (locally) using version **" + config.getProjectVersion() + "**").queue();

             }

         } catch (Exception e) {
             channel.sendMessage("Error occured while registering commands. Please report to the developers").queue();
         }

    }
}
