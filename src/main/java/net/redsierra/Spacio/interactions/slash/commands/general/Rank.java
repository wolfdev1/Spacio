package net.redsierra.Spacio.interactions.slash.commands.general;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import net.redsierra.Spacio.util.Resources;
import org.bson.Document;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Rank extends Command {


    public Rank() {
        super.setName("rank");
        super.setCategory("info");
        super.setDescription("Return a card with your level and experience if you have a level greater than zero, otherwise it will return an error.");
    }

    @Override
    public void execute(SlashCommandInteraction ev) {
        SlashCommandInteractionEvent event = ev.getEvent();
            Spacio bot = new Spacio();
            Logger logger = bot.logger;
            MongoDatabase db = null;
            try {
                db = new BotConfig().getDatabase();
                MongoCollection<Document> c = db.getCollection("users");
            String userId = event.getUser().getId();

            if(c.find(new Document("userId", userId)).first() == null) {
                event.reply("Sorry... You have not sent any messages yet! Please try again later").queue();
            } else {
                Document user = c.find(new Document("userId", userId)).first();
                assert user != null;
                int level = user.getInteger("level");
                int xp = user.getInteger("xp");

                try {
                    InputStream f = this.getImage(event.getUser(), xp, level);

                    event.replyFiles(FileUpload.fromData(f, "rank.png")).queue();
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                }


            }

        } catch (Exception e) {
            event.reply("Sorry... Something went wrong! Please try again later").queue();
            logger.error("A fatal error occurred while executing the rank command", e);
          }
    }

    public InputStream getImage(User user, int xp, int level) throws IOException, FontFormatException {
        Resources resources = new Resources();
        BufferedImage backgroundImage = ImageIO.read(resources.getResourceFile("img/rank_bg.png"));

        BufferedImage pfp = ImageIO.read(new URL(Objects.requireNonNull(user.getAvatarUrl())));

        BufferedImage image = new BufferedImage(736, 414, BufferedImage.TYPE_INT_RGB);

        Font ftest = Font.createFont(Font.TRUETYPE_FONT, resources.getResourceFile("font/OpenSans-ExtraBold.ttf"));
        Font font = ftest.deriveFont(Font.PLAIN, 34);
        Font font1 = ftest.deriveFont(Font.PLAIN, 22);
        Font font2 = ftest.deriveFont(Font.PLAIN, 38);

        Graphics g = image.getGraphics();

        g.drawImage(pfp, 91, 150, 221, 207, null);
        g.drawImage(backgroundImage, 0, 0, null);

        g.setColor(Color.decode("#ffffff"));
        g.setFont(font);

        g.drawString(user.getGlobalName(), 90, 87);

        g.setFont(font1);
        g.setColor(Color.decode("#c9daec"));
        g.drawString(user.getId(), 390, 200);

        g.setFont(font2);
        g.setColor(Color.decode("#c9daec"));
        int requiredXP = (level + 1) * 350;
        g.drawString("Level " + level, 435, 250);
        g.drawString(xp + "/" + requiredXP, 425, 310);

        g.dispose();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        }
    }
}