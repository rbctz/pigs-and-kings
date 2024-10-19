package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    public final Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] decorationsSprite;
    private static final int defaultSize = 32;
    private final Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importLevelSprites();
        importDecorationsSprite();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importDecorationsSprite() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_DECORATIONS);
        decorationsSprite = new BufferedImage[7];
        //banner
        decorationsSprite[0] = temp.getSubimage(defaultSize, defaultSize, defaultSize, 3 * defaultSize);
        //big shelf
        decorationsSprite[1] = temp.getSubimage(2 * defaultSize, defaultSize, 3 * defaultSize, defaultSize);
        //small shelf
        decorationsSprite[2] = temp.getSubimage(5 * defaultSize, defaultSize, defaultSize, defaultSize);
        //big platform
        decorationsSprite[3] = temp.getSubimage(2 * defaultSize, 2 * defaultSize, 3 * defaultSize, defaultSize);
        //small platform
        decorationsSprite[4] = temp.getSubimage(5 * defaultSize, 2 * defaultSize, defaultSize, defaultSize);
        //window 1
        decorationsSprite[5] = temp.getSubimage(2 * defaultSize, 3 * defaultSize, 2 * defaultSize, 2 * defaultSize);
        //window 2
        decorationsSprite[6] = temp.getSubimage(4 * defaultSize, 3 * defaultSize, 2 * defaultSize, 2 * defaultSize);
    }

    private void importLevelSprites() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[247];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 19; j++) {
                int index = i * 19 + j;
                levelSprite[index] = image.getSubimage(j * defaultSize, i * defaultSize, defaultSize, defaultSize);
            }
        }
    }

    public void draw(Graphics g, int levelOffset) {
        for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILE_SIZE - levelOffset, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
            }
        }
        g.drawImage(decorationsSprite[0],1 * Game.TILE_SIZE - levelOffset, 3 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 4 * Game.TILE_SIZE,  null);
        g.drawImage(decorationsSprite[2],14 * Game.TILE_SIZE - levelOffset, 7 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
        g.drawImage(decorationsSprite[2],15 * Game.TILE_SIZE - levelOffset,12 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
//        g.drawImage(decorationsSprite[5],14 * Game.TILE_SIZE - levelOffset, 7 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
//        g.drawImage(decorationsSprite[5],14 * Game.TILE_SIZE - levelOffset, 7 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
//        g.drawImage(decorationsSprite[5],14 * Game.TILE_SIZE - levelOffset, 7 * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
        for (int i = 3; i < 48; i += 10) {
            g.drawImage(decorationsSprite[5],i * Game.TILE_SIZE - levelOffset, 5 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 2 * Game.TILE_SIZE,  null);
        }
        for (int i = 8; i < 48; i += 10) {
            g.drawImage(decorationsSprite[6],i * Game.TILE_SIZE - levelOffset, 4 * Game.TILE_SIZE, 2 * Game.TILE_SIZE, 2 * Game.TILE_SIZE,  null);
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
