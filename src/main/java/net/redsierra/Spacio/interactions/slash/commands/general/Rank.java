package net.redsierra.Spacio.interactions.slash.commands.general;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.events.SlashCommandInteraction;
import net.redsierra.Spacio.interactions.Command;
import net.redsierra.Spacio.util.Resources;
import org.bson.Document;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class Rank extends Command {
    private static final int IMAGE_WIDTH = 736;
    private static final int IMAGE_HEIGHT = 414;
    private static final int PFP_WIDTH = 221;
    private static final int PFP_HEIGHT = 207;
    private static final int PFP_X = 91;
    private static final int PFP_Y = 150;
    private static final int USER_NAME_X = PFP_X - 1;
    private static final int USER_NAME_Y = PFP_Y - 63;
    private static final int LEVEL_X = IMAGE_WIDTH / 2 + 44;
    private static final int LEVEL_Y = 250;
    private static final int XP_X = IMAGE_WIDTH / 2 + 34;
    private static final int XP_Y = 310;

    private static final BufferedImage BACKGROUND_IMAGE;
    private static final Font FONT_PLAIN_34;
    private static final Font FONT_PLAIN_38;

    static {
        try {
            Resources resources = new Resources();
            BACKGROUND_IMAGE = ImageIO.read(resources.getResourceFile("img/rank_bg.png"));

            Font ftest = Font.createFont(Font.TRUETYPE_FONT, resources.getResourceFile("font/OpenSans-ExtraBold.ttf"));
            FONT_PLAIN_34 = ftest.deriveFont(Font.PLAIN, 34);
            FONT_PLAIN_38 = ftest.deriveFont(Font.PLAIN, 38);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public Rank() {
        super.setName("rank");
        super.setCategory("info");
        super.setDescription("Return a card with your level and experience if you have a level greater than zero, otherwise it will return an error.");

    }

    @Override
    public void execute(SlashCommandInteraction ev) {
        SlashCommandInteractionEvent event = ev.getEvent();
        MongoDatabase db = null;
        try {
            db = new BotConfig().getDatabase();
            MongoCollection<Document> c = db.getCollection("users");
            String userId = event.getUser().getId();

            Document userDoc = c.find(new Document("userId", userId)).first();
            if (userDoc == null) {
                event.reply("Sorry... You have not sent any messages yet! Please try again later").queue();
            } else {
                int level = userDoc.getInteger("level");
                int xp = userDoc.getInteger("xp");

                try (InputStream f = this.getImage(event.getUser(), xp, level)) {
                    event.deferReply().queue();
                    event.getHook().editOriginal("Here is your rank card!").setFiles(FileUpload.fromData(f, "rank.png")).delay(7, TimeUnit.SECONDS).queue();
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            event.reply("Sorry... Something went wrong! Please try again later").queue();
        }
    }

    public InputStream getImage(User user, int xp, int level) throws IOException, FontFormatException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        Graphics g = image.getGraphics();

        g.drawImage(ImageIO.read(new URL(user.getAvatarUrl())), PFP_X, PFP_Y, PFP_WIDTH, PFP_HEIGHT, null);
        g.drawImage(BACKGROUND_IMAGE, 0, 0, null);

        g.setColor(Color.decode("#ffffff"));
        g.setFont(FONT_PLAIN_34);

        g.drawString(user.getGlobalName(), USER_NAME_X, USER_NAME_Y);

        g.setFont(FONT_PLAIN_38);
        g.setColor(Color.decode("#c9daec"));
        int requiredXP = (level + 1) * 350;
        g.drawString("Level " + level, LEVEL_X, LEVEL_Y);
        g.drawString(xp + "/" + requiredXP, XP_X, XP_Y);

        g.dispose();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        }
    }
}
