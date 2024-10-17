package ui;

import gamestates.Gamestate;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.Buttons.*;

public class MenuButton {

    public int xPos, yPos, rowIndex, index, width, height, widthDefault, heightDefault, xDrawPos, yDrawPos;
    public int xOffsetCenter = B_WIDTH / 2;
    public Gamestate state;
    public BufferedImage[] button;
    public boolean mouseOver, mousePressed;
    public Rectangle buttonBounds;



    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadButtons();
        initBounds();
    }

    public MenuButton(int xPos, int yPos, int xDrawPos, int yDrawPos, int width, int height, int widthDefault, int heightDefault, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xDrawPos = xDrawPos;
        this.yDrawPos = yDrawPos;
        this.width = width;
        this.height = height;
        this.widthDefault = widthDefault;
        this.heightDefault = heightDefault;
        this.state = state;
        loadCustomButton(this);
        initCustomBounds(this);

    }

    private void initBounds() {
        buttonBounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void initCustomBounds(MenuButton menuButton) {
        buttonBounds = new Rectangle(menuButton.xPos, menuButton.yPos, menuButton.width, menuButton.height);
    }

    private void loadButtons() {
        button = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BUTTONS_ATLAS);
        for (int i = 0; i < button.length; i++) {
            button[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    private void loadCustomButton(MenuButton mb) {
        button = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BUTTONS_ATLAS);
        for (int i = 0; i < button.length; i++) {
            button[i] = temp.getSubimage(mb.xDrawPos + i * mb.widthDefault , mb.yDrawPos, mb.widthDefault, mb.heightDefault);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(button[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }
    public void drawCustomButton(Graphics g, MenuButton mb) {
        g.drawImage(button[index], mb.xPos - (mb.width / 2), mb.yPos, mb.width, mb.height, null);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
