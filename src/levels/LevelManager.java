package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {

    public final Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importSprites() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[247];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 19; j++) {
                int index = i * 19 + j;
                levelSprite[index] = image.getSubimage(j * 32, i * 32, 32, 32);
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
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
