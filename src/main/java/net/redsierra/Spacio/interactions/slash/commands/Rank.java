package net.redsierra.Spacio.interactions.slash.commands;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.redsierra.Spacio.Spacio;
import net.redsierra.Spacio.database.Database;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import net.redsierra.Spacio.util.Resources;
import org.bson.Document;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class Rank extends Command {


    public Rank() {
        super.setName("rank");
        super.setCategory("info");
        super.setDescription("Return a card with your level and experience if you have a level greater than zero, otherwise it will return an error.");
    }

    @Override
    public void execute(SlashCommandInteraction ev) {
        SlashCommandInteractionEvent event = ev.event();
            Spacio bot = new Spacio();
            Logger logger = bot.logger;
            Database db = null;
            try {
            try {
                db = new Database();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            MongoCollection<Document> c = db.getDatabase().getCollection("users");
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

                    event.replyFiles(FileUpload.fromData(f, "rank.png")).queueAfter(50, TimeUnit.MILLISECONDS);
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

        BufferedImage backgroundImage = null;
        URL url = new URL(Objects.requireNonNull(user.getAvatarUrl()));
        BufferedImage pfp =  ImageIO.read(url);
        try {
            backgroundImage = ImageIO.read(resources.getResourceFile("img/rank_bg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        BufferedImage image = new BufferedImage(2280, 1080, BufferedImage.TYPE_INT_RGB);
        Font ftest = Font.createFont(Font.TRUETYPE_FONT, resources.getResourceFile("font/OpenSans-ExtraBold.ttf"));
        Font font = ftest.deriveFont(Font.PLAIN, 150);
        Graphics g = image.getGraphics();
        g.setColor(Color.decode("#ddbfa2"));
        g.fillRect(0, 0, 947, 418);
        g.setFont(font);


        g.drawImage(pfp, 368, 260, 488, 488, null);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawString(user.getGlobalName(), 1053, 525);
        Font font2 = ftest.deriveFont(Font.PLAIN, 85);
        g.setFont(font2);
        g.setColor(Color.decode("#8f7b6d"));
        int requiredXP = (level + 1) * 350;
        g.drawString("Level "+level+"  XP: " + xp + "/"+requiredXP, 1055, 630);

        g.dispose();


        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image,"png", os);
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        return fis;
    }
}
