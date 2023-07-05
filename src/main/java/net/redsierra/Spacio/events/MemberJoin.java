package net.redsierra.Spacio.events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import net.redsierra.Spacio.config.BotConfig;
import net.redsierra.Spacio.util.Resources;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class MemberJoin extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        BotConfig config;
        try {
            config = new BotConfig();
        } catch (IOException  | ParseException e) {
            throw new RuntimeException(e);
        }

        // Code to generate the image

        TextChannel ch;
        ch = event.getGuild().getTextChannelById(config.getWelcomeChannelId());

        InputStream file;
        try {
            file = this.getImage(Objects.requireNonNull(event.getMember()).getUser());
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        assert ch != null;
        ch.sendFiles(FileUpload.fromData(file, "welcome.png")).queue();
    }

    public InputStream getImage(User user) throws IOException, FontFormatException {

        Resources resources = new Resources();

        BufferedImage backgroundImage = null;
        URL url = new URL(Objects.requireNonNull(user.getAvatarUrl()));
        BufferedImage pfp = ImageIO.read(url);
        try {
            backgroundImage = ImageIO.read(resources.getResourceFile("img/background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStream fontStream = resources.getResourceFile("font/OpenSans-ExtraBold.ttf");

        BufferedImage image = new BufferedImage(2280, 1080, BufferedImage.TYPE_INT_RGB);
        Font test = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        Font font = test.deriveFont(Font.PLAIN, 150);

        Graphics g = image.getGraphics();
        g.setColor(Color.decode("#9fb2c4"));
        g.fillRect(0, 0, 947, 418);
        g.setFont(font);


        g.drawImage(pfp, 368, 260, 488, 488, null);
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawString(Objects.requireNonNull(user.getGlobalName()), 1035, 550);
        Font test2 = Font.createFont(Font.PLAIN, fontStream);
        Font font2 = test2.deriveFont(Font.PLAIN, 85);
        g.setFont(font2);
        g.setColor(Color.decode("#30516f"));
        g.drawString("Welcome to the server!", 950, 620);

        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", os);

            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
