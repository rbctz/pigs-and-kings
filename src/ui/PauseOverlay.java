package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.UrmButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseOverlay {

    public Playing playing;
    public BufferedImage backgroundImg;
    public int bgX, bgY, bgWidth, bgHeight;

    public SoundButton soundButton;
    public UrmButton menuButton, replayButton, unpauseButton;
    public VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButton();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int) (295 * Game.SCALE);
        int volumeY = (int) (130 * Game.SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createUrmButtons() {
        int menuX = (int) (598 * Game.SCALE);
        int menuY = (int) (140 * Game.SCALE);
        menuButton = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
        int replayX = (int) (598 * Game.SCALE);
        int replayY = (int) (253 * Game.SCALE);
        replayButton = new UrmButton(replayX, replayY, URM_SIZE, URM_SIZE, 1);
        int unpauseX = (int) (380 * Game.SCALE);
        int unpauseY = (int) (280 * Game.SCALE);
        unpauseButton = new UrmButton(unpauseX, unpauseY, URM_SIZE, URM_SIZE, 0);

    }

    public void createSoundButton() {
        int soundX = (int) (175  * Game.SCALE);
        int soundY = (int) (195 * Game.SCALE);
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
        menuButton.update();
        replayButton.update();
        unpauseButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        //BLACK BACKGROUND
        g.setColor(new Color(0, 0, 0,150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // BANNER
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

        //SOUND BUTTON
        soundButton.draw(g);

        //URM BUTTONS
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        // VOLUME SLIDER AND BUTTON
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.mousePressed) {
            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, soundButton))
            soundButton.mousePressed = true;
        else if (isIn(e, menuButton))
            menuButton.mousePressed = true;
        else if (isIn(e, replayButton))
            replayButton.mousePressed = true;
        else if (isIn(e, unpauseButton))
            unpauseButton.mousePressed = true;
        else if (isIn(e, volumeButton))
            volumeButton.mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, soundButton)) {
            if (soundButton.mousePressed) {
                soundButton.muted = !soundButton.muted;
            }
        } else if (isIn(e, menuButton)) {
            if (menuButton.mousePressed) {
                Gamestate.state = Gamestate.MENU;
                playing.paused = false;
            }
        } else if (isIn(e, replayButton)) {
            if (replayButton.mousePressed) {
                System.out.println("REPLAY LVL!");
            }
        } else if (isIn(e, unpauseButton)) {
            if (unpauseButton.mousePressed) {
                playing.unpauseGame();
            }
        }
        soundButton.resetBools();
        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        soundButton.mouseOver = false;
        menuButton.mouseOver = false;
        replayButton.mouseOver = false;
        unpauseButton.mouseOver = false;
        volumeButton.mouseOver = false;

        if (isIn(e, soundButton))
            soundButton.mouseOver = true;
        else if (isIn(e, menuButton))
            menuButton.mouseOver = true;
        else if (isIn(e, replayButton))
            replayButton.mouseOver = true;
        else if (isIn(e, unpauseButton))
            unpauseButton.mouseOver = true;
        else if (isIn(e, volumeButton))
            volumeButton.mouseOver = true;
    }

    public boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return (pauseButton.bounds.contains(e.getX(), e.getY()));
    }
}
