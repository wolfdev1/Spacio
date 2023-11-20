package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class EditTicketStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("ticket-edit")) {
            if (!event.getChannel().getName().startsWith("ticket-")) {
                event.reply("You can only edit tickets.").setEphemeral(true).queue();
                return;
            }
            String val = event.getValues().get(0);
            switch (val) {
                case "topic":
                    event.reply("Please enter the new topic of your ticket").setEphemeral(true)
                            .addActionRow(
                                    StringSelectMenu.create("edit-ticket-type")
                                            .addOptions(SelectOption.of("Bug Report", "bug-report")
                                                    .withDescription("Report a bug in the bot or in the server")
                                                    .withEmoji(Emoji.fromUnicode("\ud83e\udd16")))
                                            .addOptions(SelectOption.of("Suggestion", "suggestion")
                                                    .withDescription("Suggest something to the server")
                                                    .withEmoji(Emoji.fromUnicode("\ud83d\udcec")))
                                            .addOptions(SelectOption.of("Doubts", "doubts")
                                                    .withDescription("Doubts & Questions about the server")
                                                    .withEmoji(Emoji.fromUnicode("\u2753")))
                                            .addOptions(SelectOption.of("Other", "other")
                                                    .withDescription("Other reasons")
                                                    .withEmoji(Emoji.fromUnicode("\ud83d\udce3")))
                                            .build()).queue();
                    break;
                case "archive":
                    TextInput verify = TextInput.create("verifyArchive", "Ticket ID", TextInputStyle.SHORT)
                            .setPlaceholder("To verify that you want to archive your ticket, please enter your ticket ID.")
                            .setRequiredRange(1, 20)
                            .build();
                    Modal modal = Modal.create("archive-ticket", "Archive Ticket")
                            .addComponents(ActionRow.of(verify))
                            .build();
                    event.replyModal(modal).queue();
                    break;
                case "delete":
                    TextInput verify2 = TextInput.create("verifyDelete", "Ticket ID", TextInputStyle.SHORT)
                            .setPlaceholder("To verify that you want to delete your ticket, please enter your ticket ID.")
                            .setRequiredRange(1, 20)
                            .build();
                    Modal modal2 = Modal.create("delete-ticket", "Delete Ticket")
                            .addComponents(ActionRow.of(verify2))
                            .build();
                    event.replyModal(modal2).queue();
                    break;
            }
        }
    }
}
