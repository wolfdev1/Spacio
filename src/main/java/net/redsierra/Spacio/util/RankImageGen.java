package net.redsierra.Spacio.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class RankImageGen {

    private static final int IMAGE_WIDTH = 736;
    private static final int IMAGE_HEIGHT = 414;
    private static final int PFP_WIDTH = 221;
    private static final int PFP_HEIGHT = 207;
    private static final int PFP_X = 91;
    private static final int PFP_Y = 150;
    private static final int USER_NAME_X = PFP_X - 1;
    private static final int USER_NAME_Y = PFP_Y - 63;
    private static final int LEVEL_X = IMAGE_WIDTH / 2 + 67;
    private static final int LEVEL_Y = 250;
    private static final int XP_X = IMAGE_WIDTH / 2 + 60;
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
            throw new ExceptionInInitializerError(e);
        }
    }

    public InputStream getImage(String name, String avatarUrl, int xp, int level) throws IOException {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        try {
            BufferedImage avatarImage = ImageIO.read(new URL(avatarUrl));

            g.drawImage(avatarImage, PFP_X, PFP_Y, PFP_WIDTH, PFP_HEIGHT, null);
            g.drawImage(BACKGROUND_IMAGE, 0, 0, null);

            g.setColor(Color.decode("#ffffff"));
            g.setFont(FONT_PLAIN_34);

            g.drawString(name, USER_NAME_X, USER_NAME_Y);

            g.setColor(Color.decode("#c9daec"));
            g.setFont(FONT_PLAIN_38);

            int requiredXP = (level + 1) * 350;

            g.drawString("Level " + level, LEVEL_X, LEVEL_Y);
            g.drawString(xp + "/" + requiredXP, XP_X, XP_Y);
        } finally {
            g.dispose();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return new ByteArrayInputStream(imageBytes);
    }
}

