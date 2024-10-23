package ui;

import entities.Player;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.StatusBar.*;

public class StatusBar {

    public Player player;
    public BufferedImage statusBar = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

    //ANIMATIONS
    public BufferedImage[] heartAnimations;
    public int heartAnimationIndex = 0;
    public int heartAnimationTick = 0;
    public int heartAnimationSpeed = 30;
    public int heartHitAnimationTick = 0;
    public int heartHitAnimationIndex = 0;
    public BufferedImage[] heartHitAnimations;
    public boolean checkHitHeart = false;

    public StatusBar(Player player) {
        this.player = player;
        loadHearts();
    }

    public void update() {
        updateHitAnimationTick();
        updateAnimationTick();
    }

    public void draw(Graphics g) {
        drawStatusBar(g);
        drawHearts(g);
    }

    public void drawStatusBar(Graphics g) {
        g.drawImage(statusBar, STATUSBAR_X, STATUSBAR_Y, STATUSBAR_WIDTH, STATUSBAR_HEIGHT, null);
    }

    public void drawHearts(Graphics g) {
        for (int i = 0; i < player.currHP; i++) {
            g.drawImage(heartAnimations[heartAnimationIndex], HEART_X_OFFSET + HEART_X * i, HEART_Y, HEART_WIDTH, HEART_HEIGHT, null);
        }
        if (checkHitHeart) {
            g.drawImage(heartHitAnimations[heartHitAnimationIndex],HEART_X_OFFSET + HEART_X * player.currHP, HEART_Y, HEART_WIDTH, HEART_HEIGHT, null);
        }
    }

    public void loadHearts() {
        heartAnimations = new BufferedImage[8];
        heartHitAnimations = new BufferedImage[2];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SMALL_HEART);
        for (int i = 0; i < heartAnimations.length; i++)
            heartAnimations[i] = temp.getSubimage(i * 18, 0, 18, 14);
        temp = LoadSave.GetSpriteAtlas(LoadSave.SMALL_HEART_HIT);
        for (int i = 0; i < heartHitAnimations.length; i++)
            heartHitAnimations[i] = temp.getSubimage(i * 18, 0, 18, 14);
    }

    public void updateAnimationTick() {
        heartAnimationTick++;
        if (heartAnimationTick >= heartAnimationSpeed) {
            heartAnimationTick = 0;
            heartAnimationIndex++;
            if (heartAnimationIndex >= 8)
                heartAnimationIndex = 0;
        }
    }

    public void updateHitAnimationTick() {
        heartHitAnimationTick++;
        if (heartHitAnimationTick >= heartAnimationSpeed * 1.5) {
            heartHitAnimationTick = 0;
            heartHitAnimationIndex++;
            if (heartHitAnimationIndex >= 2) {
                heartHitAnimationIndex = 0;
                checkHitHeart = false;
            }
        }
    }
}
