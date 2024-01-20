package net.redsierra.Spacio;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.*;

import net.redsierra.Spacio.events.automod.AntiBadWords;
import net.redsierra.Spacio.events.automod.AntiCapsSpam;
import net.redsierra.Spacio.interactions.autocompletes.MuteAutocomplete;
import net.redsierra.Spacio.interactions.menus.*;
import net.redsierra.Spacio.interactions.modals.*;
import net.redsierra.Spacio.events.automod.AntiInvites;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Spacio {

    public Logger logger = LoggerFactory.getLogger(Spacio.class);
    public static void main(String[] args)
            throws InterruptedException {
        BotConfig config = new BotConfig();

        JDA jda = JDABuilder.createDefault(config.getBotToken())
                .setActivity(Activity.listening("LoFI Beats"))
                .addEventListeners(
                        new WarnModal(),
                        new MemberJoin(),
                        new Ready(),
                        new MessageReceived(),
                        new ClearWarnModal(),
                        new KickModal(),
                        new RolesStringMenu(),
                        new GenderStringMenu(),
                        new PronounsStringMenu(),
                        new RegionStringMenu(),
                        new CategoryStringMenu(),
                        new MuteAutocomplete(),
                        new AntiCapsSpam(),
                        new EditTicketStringMenu(),
                        new DeleteTicketModal(),
                        new ArchiveTicketModal(),
                        new EditTicketTopicStringMenu(),
                        new TicketStringMenu(),
                        new AntiBadWords(),
                        new AntiInvites(),
                        new SlashCommandHandler()
                )
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .build();

        jda.awaitReady();


    }



}