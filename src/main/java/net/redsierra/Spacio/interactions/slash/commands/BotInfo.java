package net.redsierra.Spacio.interactions.slash.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.events.SlashCommandHandler;
import net.redsierra.Spacio.interactions.Command;
import java.awt.*;

public class BotInfo extends Command {

    public BotInfo() {
        super.setName("botinfo");
        super.setCategory("info");
        super.setDescription("Returns information about the bot and the project.");
    }

    @Override
    public void execute(SlashCommandInteraction commandEvent) {

        SlashCommandInteractionEvent event = commandEvent.event();

        String desc = "Spacio is a multifunctional Discord bot, it takes care of moderation and server ranking, as well as a music system.";

        BotConfig config;
        config = new BotConfig();

        event.replyEmbeds(new EmbedBuilder()
                .setAuthor("Spacio", null, event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(Color.decode("#878dc9"))
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .addField("Description", desc, false)
                .addField("Project", "Spacio is currently a private project but is developed by wolfdev1", false)
                .addField("Project Version", config.getProjectVersion(), false)
                .addField("GitHub", "[GitHub <:github:1055533215609262100>](https://github.com/wolfdev1)", false)
                .addField("Discord Library", "[JDA " + JDAInfo.VERSION + "]("+JDAInfo.GITHUB+") <:jda:1055541128771928125>", false)
                .addField("Slash Commands", "Currently bot have "+ SlashCommandHandler.getCommands().size() +" commands.", false)
                .addField("Database", "Currently bot use MongoDB", false)
                .setFooter("Spacio", event.getJDA().getSelfUser().getAvatarUrl()).build()).queue();

    }
}
