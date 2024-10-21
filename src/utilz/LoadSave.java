package utilz;

import entities.Pig;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.PIG;

public class LoadSave {

    //LEVEL
    public static final String LEVEL_ATLAS = "/Terrain (32x32).png";
    public static final String LEVEL_ONE_DATA = "/level_one_data_long.png";
    public static final String LEVEL_DECORATIONS = "/Decorations (32x32).png";

    //KING
    public static final String KING_ATTACK = "/01-King Human/Attack (78x58).png";
    public static final String KING_DEAD = "/01-King Human/Dead (78x58).png";
    public static final String KING_DOOR_IN = "/01-King Human/Door In (78x58).png";
    public static final String KING_DOOR_OUT = "/01-King Human/Door Out (78x58).png";
    public static final String KING_FALL = "/01-King Human/Fall (78x58).png";
    public static final String KING_GROUND = "/01-King Human/Ground (78x58).png";
    public static final String KING_HIT = "/01-King Human/Hit (78x58).png";
    public static final String KING_IDLE = "/01-King Human/Idle (78x58).png";
    public static final String KING_JUMP = "/01-King Human/Jump (78x58).png";
    public static final String KING_RUN = "/01-King Human/Run (78x58).png";

    //PART OF THE MENU
    public static final String BUTTONS_ATLAS = "/Orange Button Text.png";
    public static final String MENU_BACKGROUND_IMG = "/menu_bg.png";
    public static final String TITLE = "/Kings and Pigs.png";
    public static final String RIBBON = "/Ribbon_Red_3Slides.png";
    public static final String BANNER = "/Banner_Vertical.png";

    //PART OF THE PAUSE MENU
    public static final String PAUSE_BANNER = "/PAUSE_BANNER.png";
    public static final String SOUND_BUTTONS = "/sound_button.png";
    public static final String URM_BUTTONS = "/urm_buttons.png";
    public static final String VOLUME_SLIDER = "/volume_buttons.png";

    //PIG
    public static final String PIG_IDLE = "/03-Pig/Idle (34x28).png";
    public static final String PIG_RUN = "/03-Pig/Run (34x28).png";
    public static final String PIG_ATTACK = "/03-Pig/Attack (34x28).png";
    public static final String PIG_HIT = "/03-Pig/Hit (34x28).png";
    public static final String PIG_DEAD = "/03-Pig/Dead (34x28).png";

    //STATUS BAR
    public static final String STATUS_BAR = "/12-Live and Coins/Live Bar.png";
    public static final String SMALL_HEART = "/12-Live and Coins/Small Heart Idle (18x14).png";
    public static final String SMALL_HEART_HIT = "/12-Live and Coins/Small Heart Hit (18x14).png";

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

    public static ArrayList<Pig> GetPigs() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Pig> list = new ArrayList<>();

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int value = color.getGreen();
                if (value == PIG) {
                    list.add(new Pig(j * Game.TILE_SIZE, i * Game.TILE_SIZE));
                }
            }
        }
        return list;
    }

    public static int[][] GetLevelData() {

        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

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
