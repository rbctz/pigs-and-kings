package ui;

import main.Game;
import utilz.Constants;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {

    public BufferedImage backgroundImg;
    public int bgX, bgY, bgWidth, bgHeight;
    public SoundButton soundButton;

    public PauseOverlay() {
        loadBackground();
        createSoundButton();
    }

    public void createSoundButton() {
        int soundX = (int) (168  * Game.SCALE);
        int soundY = (int) (190 * Game.SCALE);
        soundButton = new SoundButton(soundX, soundY,SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BANNER);
        bgWidth = (int) (backgroundImg.getWidth() * Game.SCALE * 3);
        bgHeight = (int) (backgroundImg.getHeight() * Game.SCALE * 3);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (-80 * Game.SCALE);

    }

    public void update() {
        soundButton.update();
        System.out.println(!soundButton.muted);
    }

    public void draw(Graphics g) {
        // BANNER
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

        //SOUND BUTTON
        soundButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, soundButton))
            soundButton.mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, soundButton)) {
            if (soundButton.mousePressed) {
                soundButton.muted = !soundButton.muted;
            }
        }
        soundButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        soundButton.mouseOver = false;
        if (isIn(e, soundButton))
            soundButton.mouseOver = true;
    }

    public boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return (pauseButton.bounds.contains(e.getX(), e.getY()));
    }
}
