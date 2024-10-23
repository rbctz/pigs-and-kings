package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.Door.*;

public class Door {

    public BufferedImage[][] doorSprite;
    public int doorState = CLOSING;
    public int animationIndex;
    public int animationSpeed = 80;
    public int animationTick;

    public void update() {
        updateAnimation();
    }

    public void drawLevel1(Graphics g, int levelOffset) {
        g.drawImage(doorSprite[doorState][animationIndex], 5 * Game.TILE_SIZE - levelOffset, (int) (7.5 * Game.TILE_SIZE), 2 * Game.TILE_SIZE, (int) (2.5 * Game.TILE_SIZE), null);
        g.drawImage(doorSprite[doorState][animationIndex], 47 * Game.TILE_SIZE - levelOffset, (int) (7.5 * Game.TILE_SIZE), 2 * Game.TILE_SIZE, (int) (2.5 * Game.TILE_SIZE), null);
    }

    public void drawLevel2(Graphics g, int levelOffset) {
        g.drawImage(doorSprite[doorState][animationIndex], 5 * Game.TILE_SIZE - levelOffset, (int) (7.5 * Game.TILE_SIZE), 2 * Game.TILE_SIZE, (int) (2.5 * Game.TILE_SIZE), null);
    }

    public void importDoorSprite() {
        BufferedImage doorOpening = LoadSave.GetSpriteAtlas(LoadSave.DOOR_OPEN);
        BufferedImage doorClosing = LoadSave.GetSpriteAtlas(LoadSave.DOOR_CLOSE);
        BufferedImage doorIdle = LoadSave.GetSpriteAtlas(LoadSave.DOOR_IDLE);

        doorSprite = new BufferedImage[3][5];
        int i = 0;
        int j;
        for (j = 0; j < 1; j++) doorSprite[i][j] = doorIdle;
        i++;
        for (j = 0; j < 5; j++) doorSprite[i][j] = doorOpening.getSubimage(j * DOOR_WIDTH, 0, DOOR_WIDTH, DOOR_HEIGHT);
        i++;
        for (j = 0; j < 3; j++) doorSprite[i][j] = doorClosing.getSubimage(j * DOOR_WIDTH, 0, DOOR_WIDTH, DOOR_HEIGHT);
    }

    public void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetDoorSpriteAmount(doorState)) {
                animationIndex = 0;
                doorState = DOOR_IDLE;
            }
        }
    }

    public void resetAnimation() {
        animationIndex = 0;
        animationTick = 0;
    }

    public void resetAll() {
        resetAnimation();
        doorState = CLOSING;
    }
}
