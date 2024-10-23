package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {

    //LEVEL
    public static final String LEVEL_ATLAS = "/Terrain (32x32).png";
    public static final String LEVEL_DECORATIONS = "/Decorations (32x32).png";
    public static final String DOOR_OPEN = "/11-Door/Opening (46x56).png";
    public static final String DOOR_CLOSE = "/11-Door/Closing (46x56).png";
    public static final String DOOR_IDLE = "/11-Door/Idle.png";


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
    public static final String BIG_HEART = "/12-Live and Coins/Big Heart Idle (18x14).png";
    public static final String BIG_HEART_HIT = "/12-Live and Coins/Big Heart Hit (18x14).png";

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert file != null;
        File[] files = file.listFiles();

        assert files != null;
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < files.length; i++)
            for (int j = 0; j < files.length; j++)
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

        BufferedImage[] images = new BufferedImage[filesSorted.length];

        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

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
}
