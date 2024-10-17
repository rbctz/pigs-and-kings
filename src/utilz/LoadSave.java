package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String LEVEL_ATLAS = "/Terrain (32x32).png";
    public static final String LEVEL_ONE_DATA = "/level_one_data.png";
    public static final String BUTTONS_ATLAS = "/Orange Button Text.png";

    //PART OF THE MENU
    public static final String TITLE = "/Kings and Pigs.png";
    public static final String RIBBON = "/Ribbon_Red_3Slides.png";
    public static final String BANNER = "/Banner_Vertical.png";

    //PART OF THE PAUSE MENU
    public static final String PAUSE_BANNER = "/PAUSE_BANNER.png";
    public static final String SOUND_BUTTONS = "/sound_button.png";
    public static final String URM_BUTTONS = "/urm_buttons.png";

    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage sprite = null;
        InputStream is = LoadSave.class.getResourceAsStream(filename);
        try {
            assert is != null;
            sprite = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
    }

    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int value = color.getRed();
                if (value >= 247) {
                    value = 0;
                }
                levelData[i][j] = value;
            }
        }
        return levelData;
    }
}
