package net.redsierra.Spacio.interactions.menus;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RegionStringMenu extends ListenerAdapter {
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("region-select")) {
            String val = event.getValues().get(0);
            assert event.getMember() != null;
            assert event.getGuild() != null;
            Guild guild = event.getGuild();

            switch (val) {
                case "na":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("North America", true).get(0)).queue();
                    event.reply("You have been given the role `North America`").setEphemeral(true).queue();
                break;
                case "sa":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("South America", true).get(0)).queue();
                    event.reply("You have been given the role `South America`").setEphemeral(true).queue();
                break;
                case "eu":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Europe", true).get(0)).queue();
                    event.reply("You have been given the role `Europe`").setEphemeral(true).queue();
                break;
                case "asia":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Asia", true).get(0)).queue();
                    event.reply("You have been given the role `Asia`").setEphemeral(true).queue();
                break;
                case "africa":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Africa", true).get(0)).queue();
                    event.reply("You have been given the role `Africa`").setEphemeral(true).queue();
                break;
                case "oceania":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Oceania", true).get(0)).queue();
                    event.reply("You have been given the role `Oceania`").setEphemeral(true).queue();
                break;
                case "antarctica":
                    guild.addRoleToMember(event.getMember(), guild.getRolesByName("Antarctica", true).get(0)).queue();
                    event.reply("You have been given the role `Antarctica`").setEphemeral(true).queue();
                break;
            }
        }
    }
}
