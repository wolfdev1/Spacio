package net.redsierra.Spacio.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.redsierra.Spacio.config.BotConfig;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class InfractionLogger {

    @SuppressWarnings("ALL")
    public void createLog(String userId, String reason, String modId, String eventId, String chId, String sType, Guild guild){
        BotConfig config = null;
        try {
            config = new BotConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        EmbedBuilder eb = new EmbedBuilder()
                .setAuthor(
                        "Mod "+guild.getMemberById(modId).getUser().getAsTag(),
                        null,
                        guild.getMemberById(modId).getUser().getAvatarUrl())
                .setTitle("New Mod Log!")
                .addField("Offender", guild.getMemberById(userId).getAsMention(), false)
                .addField("Advocacy Channel", guild.getTextChannelById(chId).getAsMention(), false)
                .addField("Infraction Type", sType, false)
                .addField("Event ID", eventId, false)
                .addField("Reason", (reason.isEmpty() ? "No reason provided" : reason), false)
                .setThumbnail(guild.getMemberById(userId).getUser().getAvatarUrl())
                .setColor(Color.decode("#af94eb"))
                .setTimestamp(Instant.now())
                .setFooter("Sanction type: " + sType);

        guild.getTextChannelById(config.getLogChannelId()).sendMessageEmbeds(eb.build()).queue();

    }
}
