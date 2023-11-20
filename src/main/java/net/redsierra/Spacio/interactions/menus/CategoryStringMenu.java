package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("categories-select")) {
            String val = event.getValues().get(0);
            assert event.getMember() != null;
            assert event.getGuild() != null;
            Guild guild = event.getGuild();

            switch (val) {
                case "art":

                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Art", true).get(0)).queue();
                    event.reply("You have been given the role `Art`").setEphemeral(true).queue();
                    break;
                case "music":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Music", true).get(0)).queue();
                    event.reply("You have been given the role `Music`").setEphemeral(true).queue();
                    break;
                case "gaming":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Gaming", true).get(0)).queue();
                    event.reply("You have been given the role `Gaming`").setEphemeral(true).queue();
                    break;
                case "programming":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Programming", true).get(0)).queue();
                    event.reply("You have been given the role `Programming`").setEphemeral(true).queue();
                    break;
            }
        }
    }
}
