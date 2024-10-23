package ui;

import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {

    public BufferedImage[][] soundImgs;
    public boolean mouseOver, mousePressed;
    public boolean muted = false;
    public int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    public void loadSoundImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int i = 0; i < soundImgs.length; i++) {
            for (int j = 0; j < soundImgs[i].length; j++) {
                soundImgs[i][j] = temp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;
        colIndex = 0;
        if (mouseOver)
            colIndex = 1;
        if(mousePressed)
            colIndex = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}
