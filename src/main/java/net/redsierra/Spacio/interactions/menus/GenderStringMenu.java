package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GenderStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("gender-select")) {
            String val = event.getValues().get(0);
            assert event.getMember() != null;
            assert event.getGuild() != null;
            Guild guild = event.getGuild();

            switch (val) {
                case "male":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("male", true).get(0)).queue();
                    event.reply("You have been given the role `Male`").setEphemeral(true).queue();
                break;
                case "female":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("female", true).get(0)).queue();
                    event.reply("You have been given the role `Female`").setEphemeral(true).queue();
                break;
                case "non-binary":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("non-binary", true).get(0)).queue();
                    event.reply("You have been given the role `Non-Binary`").setEphemeral(true).queue();
                break;
                case "genderqueer":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("genderqueer", true).get(0)).queue();
                    event.reply("You have been given the role `Genderqueer`").setEphemeral(true).queue();
                break;
            }
        }
    }
}
