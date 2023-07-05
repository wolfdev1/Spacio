package net.redsierra.Spacio.interactions.slash.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;

import java.awt.*;
import java.time.Instant;

public class UnMute extends Command {

    public UnMute() {
        super.setName("unmute");
        super.setCategory("mod");
        super.setDescription("It unmutes a user.");
    }

    @Override
    public void execute(SlashCommandInteraction commandEvent) {

        SlashCommandInteractionEvent event = commandEvent.event();

        OptionMapping option = event.getOption("user");
        assert option != null;
        Member member = option.getAsMember();
        assert member != null;

        if(member.isTimedOut()) {
            member.removeTimeout().queue();

            event.replyEmbeds(
                    new EmbedBuilder()
                            .setAuthor(member.getUser().getGlobalName() + " unmuted", null, member.getUser().getAvatarUrl())
                            .setTimestamp(Instant.now())
                            .setColor(Color.decode("#ada1e6"))
                            .setFooter("Powered by Spacio")
                            .build()
            ).queue();

        } else {
            event.reply("This user is not muted. Please select a valid user").setEphemeral(true).queue();
        }

    }
}
