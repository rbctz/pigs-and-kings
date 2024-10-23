package levels;

import entities.Pig;
import main.Game;
import utilz.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPigs;

public class Level {

    public BufferedImage image;
    public int[][] levelData;
    public ArrayList<Pig> pigs;
    public int levelTilesWidth;
    public int maxTilesOffset;
    public int maxLevelOffsetX;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        calculateLevelOffsets();
    }

    public void calculateLevelOffsets() {
        levelTilesWidth = image.getWidth();
        maxTilesOffset = levelTilesWidth  - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILE_SIZE * maxTilesOffset;
    }

    public void createEnemies() {
        pigs = GetPigs(image);
    }

    public void createLevelData() {
        levelData = GetLevelData(image);
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];

    }

    public int[][] getLevelData() {
        return levelData;
    }
}
