package levels;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;
import levels.Door.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.DEFAULT_SIZE;


public class LevelManager {

    public final Game game;
    public BufferedImage[] levelSprite;


    public Door door = new Door();
    public Decorations decorations = new Decorations();


    public ArrayList<Level> levels;
    public int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importLevelSprites();
        decorations.importDecorationsSprite();
        door.importDoorSprite();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void update() {
        door.update();
    }

    public void draw(Graphics g, int levelOffset) {
        for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILE_SIZE - levelOffset, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE,  null);
            }
        }
        if (levelIndex == 0) {
            door.drawLevel1(g, levelOffset);
            decorations.drawLevel1(g, levelOffset);
        }
        if (levelIndex == 1) {
            door.drawLevel2(g, levelOffset);
        }
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.playing.enemyManager.loadEnemies(newLevel);
        game.playing.player.loadLevelData(newLevel.getLevelData());
        game.playing.maxLevelOffsetX = newLevel.maxLevelOffsetX;
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage image : allLevels) {
            levels.add(new Level(image));
        }
    }

    private void importLevelSprites() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[247];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 19; j++) {
                int index = i * 19 + j;
                levelSprite[index] = image.getSubimage(j * DEFAULT_SIZE, i * DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
            }
        }
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
