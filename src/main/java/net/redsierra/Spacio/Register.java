package net.redsierra.Spacio;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.redsierra.Spacio.config.BotConfig;

public class Register {

    public void reigsterCommands(JDA jda) {
        BotConfig config = new BotConfig();
        Guild guild = jda.getGuildById(config.getDefaultGuildId());
        assert guild != null;
        guild.updateCommands().addCommands(
                Commands.slash("warn", "Send a warning to the selected user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS)),
                Commands.slash("rank", "Get your rank and xp in the server."),
                Commands.slash("warnings", "Get a list of all warnings for a user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS))
                        .addOption(OptionType.USER, "user", "The user to get the warnings for.", true),
                Commands.slash("clearwarn", "Clear a warning from a user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS)),
                Commands.slash("join", "Join the voice channel you are in."),
                Commands.slash("play", "Play a song.")
                        .addOption(OptionType.STRING, "song", "The song to play. (url or name)", true),
                Commands.slash("skip", "Skip the current song."),
                Commands.slash("queue", "Get the current song queue."),
                Commands.slash("forceskip", "Force skip the current song.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL)),
                Commands.slash("botinfo", "Get info about the bot."),
                Commands.slash("avatar", "Get the avatar of a user.")
                        .addOption(OptionType.USER, "user", "The user to get the avatar of.", false),
                Commands.slash("report", "Report a user who has broken a rule.")
                        .addOption(OptionType.USER, "user", "The user to report.", true)
                        .addOption(OptionType.STRING, "reason", "The reason for the report.", true)
                        .addOption(OptionType.ATTACHMENT, "evidence", "Evidence for the report.", false),
                Commands.slash("help", "Get help with a command.")
                        .addOption(OptionType.STRING, "command", "The command to get help with.", false, true),
                Commands.slash("mute", "Mute a user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.USER, "user", "The user to mute.", true)
                        .addOption(OptionType.STRING, "reason", "The reason for the mute.", true)
                        .addOption(OptionType.STRING, "time", "The time for the mute.", false, true),
                Commands.slash("whois", "Get info about a user.")
                        .addOption(OptionType.USER, "user", "The user to get info about.", true),
                Commands.slash("unmute", "Unmute a user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.USER, "user", "The user to unmute.", true),
                Commands.slash("setcommandschannel", "Set the commands channel.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.CHANNEL, "channel", "The channel to set as the commands channel.", true),
                Commands.slash("addxpchannel", "Add a channel to the xp channels.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.CHANNEL, "channel", "The channel to add to the xp channels.", true),
                Commands.slash("removexpchannel", "Remove a channel from the xp channels.")
                                .addOption(OptionType.CHANNEL, "channel", "The channel to remove from the xp channels.", true),
                Commands.slash("setreportschannel", "Set the reports channel.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.CHANNEL, "channel", "The channel to set as the reports channel.", true),
                Commands.slash("setwelcomechannel", "Set the welcome channel.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
                        .addOption(OptionType.CHANNEL, "channel", "The channel to set as the welcome channel.", true),
                Commands.slash("fact", "Get a random fact."),
                Commands.slash("kick", "Kick a user.")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS)),
                Commands.slash("ticket", "Create a ticket.")

        ).queue();
    }
}