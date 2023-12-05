package net.redsierra.Spacio.interactions.slash.commands.general;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import org.bson.Document;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class Leaderboard extends Command {

    public Leaderboard() {
        super.setName("leaderboard");
        super.setCategory("info");
        super.setDescription("Returns the top 5 users with the most XP in the guild.");
    }
    @Override
    public void execute(SlashCommandInteraction commandEvent) {
        MongoDatabase db = new BotConfig().getDatabase();
        MongoCollection<Document> collection = db.getCollection("users");
        var cursor = collection.find().sort(new Document("xp", -1)).limit(5);
        StringBuilder sb = new StringBuilder();

        AtomicInteger i = new AtomicInteger(0);

        cursor.forEach(document -> {

            int index = i.incrementAndGet();
            sb.append(String.format("> **%d.** %s - %d\n", index, document.getString("name"), document.getLong("xp")));
        });

        EmbedBuilder embed = new EmbedBuilder();

        assert commandEvent.event().getGuild() != null;
        Member member = commandEvent.event().getGuild().getSelfMember();
        embed.setTitle("Leaderboard");
        embed.setAuthor(member.getEffectiveName(), null, member.getUser().getAvatarUrl());
        embed.setColor(new BotConfig().getSystemColor());
        embed.setTimestamp(Instant.now());
        embed.setFooter("API: https://spacio.2.us-1.fl0.io", commandEvent.event().getUser().getAvatarUrl());
        embed.setDescription("Top 5 users with most XP.\n\n"+sb);

        InteractionHook hook = commandEvent.event().getHook();
        commandEvent.event().deferReply().queue();
        hook.editOriginalEmbeds(embed.build()).queue();

    }
}
