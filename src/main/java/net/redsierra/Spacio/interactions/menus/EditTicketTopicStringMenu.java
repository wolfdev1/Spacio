package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.redsierra.Spacio.config.BotConfig;

public class EditTicketTopicStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("edit-ticket-type")) {
            assert event.getGuild() != null;
            TextChannel channel = event.getGuild().getTextChannelById(event.getChannel().getId());
            assert channel != null;
            String val = event.getValues().get(0);

            switch (val) {
                case "bug-report":
                    channel.getManager().setTopic("This ticket is about a bug report.").queue();
                    event.reply("Ticket topic changed to `Bug Report`").setEphemeral(true).queue();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setAuthor(event.getUser().getGlobalName() + " changed ticket topic", null, event.getUser().getAvatarUrl())
                            .setDescription("Please describe your bug report in the following format:\n\n" +
                                    "**Bug Report**\n" +
                                    "What is the bug?\n" +
                                    "How can we reproduce it?\n" +
                                    "\nTicket ID is: `" + event.getInteraction().getId() + "`"
                            )
                            .setColor(new BotConfig().getSystemColor())
                            .setFooter("Powered by Spacio");
                    channel.sendMessageEmbeds(builder.build()).queue();
                    break;
                case "suggestion":
                    channel.getManager().setTopic("This ticket is about an user suggestion.").queue();
                    event.reply("Ticket topic changed to `Suggestion`").setEphemeral(true).queue();
                    EmbedBuilder builder2 = new EmbedBuilder();
                    builder2.setAuthor(event.getUser().getGlobalName() + " changed ticket topic", null, event.getUser().getAvatarUrl())
                            .setDescription("Please describe your suggestion in the following format:\n\n" +
                                    "**Suggestion**\n" +
                                    "What is your suggestion?\n" +
                                    "\nTicket ID is: `" + event.getInteraction().getId() + "`"
                            )
                            .setColor(new BotConfig().getSystemColor())
                            .setFooter("Powered by Spacio");
                    channel.sendMessageEmbeds(builder2.build()).queue();
                    break;
                case "doubts":
                    channel.getManager().setTopic("This ticket is about a doubt.").queue();
                    event.reply("Ticket topic changed to `Doubts`").setEphemeral(true).queue();
                    EmbedBuilder builder3 = new EmbedBuilder();
                    builder3.setAuthor(event.getUser().getGlobalName() + " changed ticket topic", null, event.getUser().getAvatarUrl())
                            .setDescription("Please describe your doubts in the following format:\n\n" +
                                    "**Doubts**\n" +
                                    "What is your doubt?\n" +
                                    "\nTicket ID is: `" + event.getInteraction().getId() + "`"
                            )
                            .setColor(new BotConfig().getSystemColor())
                            .setFooter("Powered by Spacio");
                    channel.sendMessageEmbeds(builder3.build()).queue();
                    break;
                case "other":
                    channel.getManager().setTopic("This ticket is about another topic.").queue();
                    event.reply("Ticket topic changed to `Other`").setEphemeral(true).queue();
                    EmbedBuilder builder4 = new EmbedBuilder();
                    builder4.setAuthor(event.getUser().getGlobalName() + " changed ticket topic", null, event.getUser().getAvatarUrl())
                            .setDescription("Please describe your topic in the following format:\n\n" +
                                    "**Topic**\n" +
                                    "What is your topic?\n" +
                                    "\nTicket ID is: `" + event.getInteraction().getId() + "`"
                            )
                            .setColor(new BotConfig().getSystemColor())
                            .setFooter("Powered by Spacio");
                    channel.sendMessageEmbeds(builder4.build()).queue();
                    break;
            }
        }
    }
}
