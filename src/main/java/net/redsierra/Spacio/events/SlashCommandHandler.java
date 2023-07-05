package net.redsierra.Spacio.events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.interactions.Command;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.HashMap;

public class SlashCommandHandler extends ListenerAdapter {

    private static final HashMap<String, Command> commands = new HashMap<>();

    public static void registerCommand(String name, Command command) {

        commands.put(name, command);
        new Spacio().logger.info("Registered command " + name);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        Spacio bot = new Spacio();
        BotConfig config;
        try {
            config = new BotConfig();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        if (commands.containsKey(event.getName())) {

            if (!event.getChannel().getId().equals(config.getCommandsChannel())) {
                assert event.getMember() != null;
                if (!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {

                    event.reply("You can't use commands in this channel.").setEphemeral(true).queue();
                    return;
                }
            }

            Command command = commands.get(event.getName());
            command.execute(new SlashCommandInteraction(event));

            bot.logger.info("Attepting to execute command '" + event.getName() + "' from user " + event.getUser().getGlobalName());
        }

    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    public static HashMap<String, Command> getCommandsByCategory(String category) {
        try {
            HashMap<String, Command> commandsByCategory = new HashMap<>();
            for (String commandName : commands.keySet()) {
                Command command = commands.get(commandName);
                if (command.getCategory().equals(category)) {
                    commandsByCategory.put(commandName, command);
                }
            }
            return commandsByCategory;
        } catch (Exception e) {
            new Spacio().logger.error("Error while getting commands by category: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Command getCommand(String name) {
        return commands.get(name);
    }
}