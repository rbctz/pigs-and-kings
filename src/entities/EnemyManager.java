package entities;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstans.*;

public class EnemyManager {

    public Playing playing;
    public BufferedImage[][] pigAnimations;
    public ArrayList<Pig> pigs = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void update() {
        for (Pig pig : pigs) {
            pig.update();
        }
    }

    public void draw(Graphics g) {
        drawPigs(g);
    }

    private void drawPigs(Graphics g) {
        for (Pig pig : pigs) {
            g.drawImage(pigAnimations[pig.enemyState][pig.animationIndex], (int) pig.hitbox.x, (int) pig.hitbox.y, null);
        }
    }

    private void loadEnemyImages() {
        pigAnimations = new BufferedImage[5][11];
        BufferedImage idle = LoadSave.GetSpriteAtlas(LoadSave.PIG_IDLE);
        BufferedImage run = LoadSave.GetSpriteAtlas(LoadSave.PIG_RUN);
        BufferedImage attack = LoadSave.GetSpriteAtlas(LoadSave.PIG_ATTACK);
        BufferedImage hit = LoadSave.GetSpriteAtlas(LoadSave.PIG_HIT);
        BufferedImage dead = LoadSave.GetSpriteAtlas(LoadSave.PIG_DEAD);

        int i = 0, j = 0;
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
}