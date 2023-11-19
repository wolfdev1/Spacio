package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.redsierra.Spacio.config.BotConfig;

public class TicketStringMenu extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("ticket-type")) {
            String val = event.getValues().get(0);
            Category category = event.getGuild().getCategoriesByName("Tickets", true).get(0);

            if (category == null) {
                event.getGuild().createCategory("Tickets").queue();

                event.reply("Sorry, but the ticket category was not created yet. Please try again.").setEphemeral(true).queue();
            } else {
                String chName = "ticket-" + event.getInteraction().getId();
                category.createTextChannel(chName)
                        .addPermissionOverride(event.getGuild().getPublicRole(), 0, Permission.VIEW_CHANNEL.getRawValue())
                        .addPermissionOverride(event.getGuild().getRolesByName("Support", true).get(0), Permission.VIEW_CHANNEL.getRawValue(), 0)
                        .addMemberPermissionOverride(event.getUser().getIdLong(), Permission.VIEW_CHANNEL.getRawValue(), 0)
                        .addMemberPermissionOverride(event.getUser().getIdLong(), Permission.MESSAGE_SEND.getRawValue(), 0)
                        .addMemberPermissionOverride(event.getUser().getIdLong(), Permission.MESSAGE_HISTORY.getRawValue(), 0)
                        .addMemberPermissionOverride(event.getUser().getIdLong(), Permission.MESSAGE_ATTACH_FILES.getRawValue(), 0)
                        .queue();
                try {
                    Thread.sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextChannel channel = event.getGuild().getTextChannelsByName(chName, true).get(0);
                switch (val) {
                    case "bug-report":
                        EmbedBuilder bugReport = new EmbedBuilder()
                                .setAuthor(event.getUser().getGlobalName() + " opened a ticket", null, event.getUser().getAvatarUrl())
                                .setDescription("Please describe your bug report in the following format:\n\n" +
                                        "**Bug Report**\n" +
                                        "What is the bug?\n" +
                                        "How can we reproduce it?\n")
                                .setColor(new BotConfig().getSystemColor())
                                .setFooter("Powered by Spacio");
                        channel.sendMessageEmbeds(bugReport.build()).queue();
                        event.reply(event.getUser().getAsMention() + " ticket created with the topic `Bug Report` at " + channel.getAsMention()).setEphemeral(true).queue();
                        break;
                    case "suggestion":
                        event.reply(event.getUser().getAsMention() + " ticket created with the topic `Suggestion` at " + channel.getAsMention()).setEphemeral(true).queue();
                        EmbedBuilder suggestion = new EmbedBuilder()
                                .setAuthor(event.getUser().getGlobalName() + " opened a ticket", null, event.getUser().getAvatarUrl())
                                .setDescription("Please describe your suggestion in the following format:\n\n" +
                                        "**Suggestion**\n" +
                                        "What is your suggestion?\n" +
                                        "Why do you think it should be added?\n")
                                .setColor(new BotConfig().getSystemColor())
                                .setFooter("Powered by Spacio");
                        channel.sendMessageEmbeds(suggestion.build()).queue();
                        break;
                    case "doubts":
                        event.reply(event.getUser().getAsMention() + " ticket created with the topic `Doubts` at " + channel.getAsMention()).setEphemeral(true).queue();
                        EmbedBuilder doubts = new EmbedBuilder()
                                .setAuthor(event.getUser().getGlobalName() + " opened a ticket", null, event.getUser().getAvatarUrl())
                                .setDescription("Please describe your doubts in the following format:\n\n" +
                                        "**Doubts**\n" +
                                        "What is your doubt?\n" +
                                        "Why do you have this doubt?\n")
                                .setColor(new BotConfig().getSystemColor())
                                .setFooter("Powered by Spacio");
                        channel.sendMessageEmbeds(doubts.build()).queue();
                        break;
                    case "other":
                        EmbedBuilder other = new EmbedBuilder()
                                .setAuthor(event.getUser().getGlobalName() + " opened a ticket", null, event.getUser().getAvatarUrl())
                                .setDescription("Please describe your ticket in the following format:\n\n" +
                                        "**Other**\n" +
                                        "What is your ticket?\n" +
                                        "Why do you have this ticket?\n")
                                .setColor(new BotConfig().getSystemColor())
                                .setFooter("Powered by Spacio");
                        event.reply(event.getUser().getAsMention() + " ticket created with the topic `Other` at " + channel.getAsMention()).setEphemeral(true).queue();
                        channel.sendMessageEmbeds(other.build()).queue();
                        break;
                }
            }
        }
    }
}
