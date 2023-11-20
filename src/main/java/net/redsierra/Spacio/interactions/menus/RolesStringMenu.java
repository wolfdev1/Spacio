package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class RolesStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("roles")) {
            String val = event.getValues().get(0);
            switch (val) {
                case "region":
                    event.reply("Please select your region.")
                            .addActionRow(
                                    StringSelectMenu.create("region-select")
                                            .addOptions(SelectOption.of("North America", "na")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("North America"))
                                            .addOptions(SelectOption.of("South America", "sa")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("South America"))
                                            .addOptions(SelectOption.of("Europe", "eu")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("Europe"))
                                            .addOptions(SelectOption.of("Asia", "asia")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("Asia"))
                                            .addOptions(SelectOption.of("Africa", "africa")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("Africa"))
                                            .addOptions(SelectOption.of("Oceania", "oceania")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("Oceania"))
                                            .addOptions(SelectOption.of("Antarctica", "antarctica")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udf0d"))
                                                    .withDescription("Antarctica"))
                                            .build()
                            ).queue();
                break;
                case "gender":
                    event.reply("Please select the gender you identify with.")
                            .addActionRow(
                                    StringSelectMenu.create("gender-select")
                                            .addOptions(SelectOption.of("Male", "male")
                                                    .withEmoji(Emoji.fromUnicode("\u2642\ufe0f")))
                                            .addOptions(SelectOption.of("Female", "female")
                                                    .withEmoji(Emoji.fromUnicode("\u2640\ufe0f")))
                                            .addOptions(SelectOption.of("Non-Binary", "non-binary"))
                                            .addOptions(SelectOption.of("Genderqueer", "genderqueer"))

                                    .build()
                            ).queue();
                break;
                case "categories":
                    event.reply("Please select the categories you are interested in.")
                            .addActionRow(
                                    StringSelectMenu.create("categories-select")
                                            .addOptions(SelectOption.of("Art", "art")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udfa8")))
                                            .addOptions(SelectOption.of("Music", "music")
                                                    .withEmoji(Emoji.fromUnicode("\ud83c\udfb5")))
                                            .addOptions(SelectOption.of("Gaming", "gaming")
                                                    .withEmoji(Emoji.fromUnicode("\uD83C\uDFAE")))
                                            .addOptions(SelectOption.of("Programming", "programming")
                                                    .withEmoji(Emoji.fromUnicode("\ud83d\ude80")))
                                    .build()
                            ).queue();
                break;
                case "pronouns":
                    event.reply("Please select the pronouns you use.")
                            .addActionRow(
                                    StringSelectMenu.create("pronouns-select")
                                            .addOptions(SelectOption.of("He/Him", "he-him"))
                                            .addOptions(SelectOption.of("She/Her", "she-her"))
                                            .addOptions(SelectOption.of("They/Them", "they-them"))
                                            .addOptions(SelectOption.of("Ask", "ask"))
                                    .build()
                            ).queue();
            }
        }
    }
}
