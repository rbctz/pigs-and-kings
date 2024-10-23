package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.DEFAULT_SIZE;

public class Decorations {
    public BufferedImage[] decorationsSprite;

    public void importDecorationsSprite() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_DECORATIONS);
        decorationsSprite = new BufferedImage[7];
        //banner
        decorationsSprite[0] = temp.getSubimage(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE, 3 * DEFAULT_SIZE);
        //big shelf
        decorationsSprite[1] = temp.getSubimage(2 * DEFAULT_SIZE, DEFAULT_SIZE, 3 * DEFAULT_SIZE, DEFAULT_SIZE);
        //small shelf
        decorationsSprite[2] = temp.getSubimage(5 * DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
        //big platform
        decorationsSprite[3] = temp.getSubimage(2 * DEFAULT_SIZE, 2 * DEFAULT_SIZE, 3 * DEFAULT_SIZE, DEFAULT_SIZE);
        //small platform
        decorationsSprite[4] = temp.getSubimage(5 * DEFAULT_SIZE, 2 * DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
        //window 1
        decorationsSprite[5] = temp.getSubimage(2 * DEFAULT_SIZE, 3 * DEFAULT_SIZE, 2 * DEFAULT_SIZE, 2 * DEFAULT_SIZE);
        //window 2
        decorationsSprite[6] = temp.getSubimage(4 * DEFAULT_SIZE, 3 * DEFAULT_SIZE, 2 * DEFAULT_SIZE, 2 * DEFAULT_SIZE);
    }

    public void drawLevel1(Graphics g, int levelOffset) {
        g.drawImage(decorationsSprite[0],1 * Game.TILE_SIZE - levelOffset, 3 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 4 * Game.TILE_SIZE,  null);
        g.drawImage(decorationsSprite[2],14 * Game.TILE_SIZE - levelOffset, 7 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
        g.drawImage(decorationsSprite[2],15 * Game.TILE_SIZE - levelOffset,12 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);

        for (int k = 3; k < 48; k += 10) {
            g.drawImage(decorationsSprite[5],k * Game.TILE_SIZE - levelOffset, 5 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 2 * Game.TILE_SIZE,  null);
        }
        for (int k = 8; k < 48; k += 10) {
            g.drawImage(decorationsSprite[6],k * Game.TILE_SIZE - levelOffset, 4 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 2 * Game.TILE_SIZE,  null);
        }
    }
}
