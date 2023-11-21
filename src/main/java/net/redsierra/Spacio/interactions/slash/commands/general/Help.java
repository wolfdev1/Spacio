package net.redsierra.Spacio.interactions.slash.commands.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.events.SlashCommandHandler;
import net.redsierra.Spacio.interactions.Command;

import java.time.Instant;
import java.util.List;
public class Help extends Command {


    public Help() {
        super.setName("help");
        super.setCategory("info");
        super.setDescription("It returns the information of all the categories of the commands and their functions," +
                " it can also return the specific information of a command.");
    }

    @Override
    public void execute(SlashCommandInteraction commandEvent) {

        SlashCommandInteractionEvent event = commandEvent.event();

        if(event.getOption("command") == null) {
            assert event.getGuild() != null;

            List<String> info = SlashCommandHandler.getCommandsByCategory("info")
                    .values().stream().map(Command::getName).toList();
            List<String> icmds = event.getGuild().retrieveCommands()
                    .complete()
                    .stream()
                    .filter(c -> info.contains(c.getName()))
                    .map(net.dv8tion.jda.api.interactions.commands.Command::getAsMention).toList();

            List<String> music = SlashCommandHandler.getCommandsByCategory("music")
                    .values().stream().map(Command::getName).toList();

            List<String> mcmds = event.getGuild().retrieveCommands()
                    .complete()
                    .stream()
                    .filter(c -> music.contains(c.getName()))
                    .map(net.dv8tion.jda.api.interactions.commands.Command::getAsMention).toList();

            List<String> mod = SlashCommandHandler.getCommandsByCategory("mod")
                    .values().stream().map(Command::getName).toList();

            List<String> mdcmds = event.getGuild().retrieveCommands()
                    .complete()
                    .stream()
                    .filter(c -> mod.contains(c.getName()))
                    .map(net.dv8tion.jda.api.interactions.commands.Command::getAsMention).toList();

            List<String> misc = SlashCommandHandler.getCommandsByCategory("misc")
                    .values().stream().map(Command::getName).toList();

            List<String> mscmds = event.getGuild().retrieveCommands()
                    .complete()
                    .stream()
                    .filter(c -> misc.contains(c.getName()))
                    .map(net.dv8tion.jda.api.interactions.commands.Command::getAsMention).toList();


            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor("Spacio", null, event.getJDA().getSelfUser().getAvatarUrl())
                    .setColor(new BotConfig().getSystemColor())
                    .setTitle("Spacio Help")
                    .setDescription("**Description**\nHello! I'm Spacio, a bot made by RedSierra. I'm currently on development, but I can do some things. Here is a list of all my commands")
                    .addField("Informative Commands", String.join(", ", icmds), false)
                    .addField("Music Commands", String.join(", ", mcmds), false)
                    .addField("Misc Commands", String.join(", ", mscmds), false)
                    .addField("Moderation Commands", String.join(", ", mdcmds), false)
                    .setTimestamp(Instant.now())
                    .setFooter("Requested by " + event.getUser().getGlobalName(), event.getUser().getAvatarUrl());
            event.deferReply().queue();
            InteractionHook hook = event.getHook();
            hook.sendMessage("Loading...").setEphemeral(true).queue();
            hook.editOriginal("Here is the Help main page").setEmbeds (embed.build()).queueAfter(5, java.util.concurrent.TimeUnit.SECONDS);

        } else {
            OptionMapping option = event.getOption("command");
            assert option != null;
            String name = option.getAsString();
            if(SlashCommandHandler.getCommand(name) == null) {
                event.reply("Command not found.").queue();
            } else {
                Command command = SlashCommandHandler.getCommand(name);

                assert event.getGuild() != null;
                String finalName = name;
                String commandId = event
                        .getGuild()
                        .retrieveCommands()
                        .complete()
                        .stream().filter(cmd -> cmd.getName().equals(finalName)).findFirst().orElseThrow().getId();

                String category = command.getCategory().substring(0,1).toUpperCase() + command.getCategory().substring(1);
                name =command.getName().substring(0, 1).toUpperCase() + command.getName().substring(1);

                EmbedBuilder embed = new EmbedBuilder()
                        .setAuthor("Spacio", null, event.getJDA().getSelfUser().getAvatarUrl())
                        .setColor(new BotConfig().getSystemColor())
                        .setTitle(name + " Help")
                        .setDescription(command.getDescription())
                        .addField("Name", command.getName(), false)
                        .addField("Category", category, false)
                        .addField("Usage", "</"+finalName + ":" + commandId + ">" , false)
                        .setTimestamp(Instant.now())
                        .setFooter("Requested by " + event.getUser().getGlobalName(), event.getUser().getAvatarUrl());

                event.deferReply().queue();
                InteractionHook hook = event.getHook();
                hook.sendMessage("Loading...").setEphemeral(true).queue();
                hook.editOriginal("Here is the help page for the selected command").setEmbeds(embed.build()).queueAfter(5, java.util.concurrent.TimeUnit.SECONDS);

            }

        }
    }
}