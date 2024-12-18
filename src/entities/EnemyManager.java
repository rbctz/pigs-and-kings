package entities;

import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.PlayerConstants.PLAYER_DAMAGE;
import static utilz.HelpMethods.GetPigs;

public class EnemyManager {

    public Playing playing;
    public BufferedImage[][] pigAnimations;
    public ArrayList<Pig> pigs = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void update(int [][] levelData, Player player) {
        for (Pig pig : pigs) {
            if (pig.active)
                pig.update(levelData, player);
        }
    }

    public void draw(Graphics g, int levelOffset) {
        drawPigs(g, levelOffset);
    }

    public void loadEnemies(Level level) {
        pigs = GetPigs(level.image);
    }

    public void drawPigs(Graphics g, int levelOffset) {
        for (Pig pig : pigs) {
            if (pig.active) {
//                pig.drawHitbox(g, levelOffset);
//                pig.drawAttackBox(g, levelOffset);
                if (pig.xSpeed < 0)
                    g.drawImage(pigAnimations[pig.enemyState][pig.animationIndex],
                            (int) pig.hitbox.x - levelOffset - PIG_X_OFFSET, (int) pig.hitbox.y - PIG_Y_OFFSET,
                            PIG_WIDTH, PIG_HEIGHT, null);
                else
                    g.drawImage(pigAnimations[pig.enemyState][pig.animationIndex],
                            (int) (pig.hitbox.x - levelOffset - PIG_X_OFFSET + (float) PIG_WIDTH / 2 + 22 * Game.SCALE), (int) pig.hitbox.y - PIG_Y_OFFSET,
                            -PIG_WIDTH, PIG_HEIGHT, null);
            }
        }
    }

    public void loadEnemyImages() {
        loadPigSprites();
    }

    public void loadPigSprites() {
        pigAnimations = new BufferedImage[5][11];

        BufferedImage idle = LoadSave.GetSpriteAtlas(LoadSave.PIG_IDLE);
        BufferedImage run = LoadSave.GetSpriteAtlas(LoadSave.PIG_RUN);
        BufferedImage attack = LoadSave.GetSpriteAtlas(LoadSave.PIG_ATTACK);
        BufferedImage hit = LoadSave.GetSpriteAtlas(LoadSave.PIG_HIT);
        BufferedImage dead = LoadSave.GetSpriteAtlas(LoadSave.PIG_DEAD);

        int i = 0, j;
        for (j = 0; j < 11; j++) pigAnimations[i][j] = createSubImg(idle, j);
        i++;
        for (j = 0; j < 6; j++) pigAnimations[i][j] = createSubImg(run, j);
        i++;
        for (j = 0; j < 5; j++) pigAnimations[i][j] = createSubImg(attack, j);
        i++;
        for (j = 0; j < 2; j++) pigAnimations[i][j] = createSubImg(hit, j);
        i++;
        for (j = 0; j < 4; j++) pigAnimations[i][j] = createSubImg(dead, j);
    }

    public BufferedImage createSubImg(BufferedImage image, int row) {
        return image.getSubimage( PIG_WIDTH_DEFAULT * row, 0, PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Pig pig : pigs)
            if (pig.active)
                if (attackBox.intersects(pig.hitbox)) {
                    pig.hurt(PLAYER_DAMAGE);
                    return;
                }
    }

    public void resetAllEnemies() {
        for (Pig pig : pigs)
            pig.resetEnemy();
    }
}
