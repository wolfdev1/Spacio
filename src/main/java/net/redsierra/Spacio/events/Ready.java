package net.redsierra.Spacio.events;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.interactions.slash.commands.Mute;
import net.redsierra.Spacio.interactions.slash.commands.*;
import net.redsierra.Spacio.interactions.slash.commands.music.*;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Ready extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        BotConfig config;

        try {
            config = new BotConfig();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        Guild guild = event.getJDA().getGuildById(config.getDefaultGuildId());
        assert guild != null;
        TextChannel channel = guild.getTextChannelById(config.getCommandsChannel());
        assert channel != null;
         try {

             SlashCommandHandler.registerCommand(new Rank().getName(), new Rank());
             SlashCommandHandler.registerCommand(new Warn().getName(), new Warn());
             SlashCommandHandler.registerCommand(new ClearWarn().getName(), new ClearWarn());
             SlashCommandHandler.registerCommand(new Warnings().getName(), new Warnings());
             SlashCommandHandler.registerCommand(new ForceSkip().getName(), new ForceSkip());
             SlashCommandHandler.registerCommand(new Skip().getName(), new Skip());
             SlashCommandHandler.registerCommand(new Queue().getName(), new Queue());
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