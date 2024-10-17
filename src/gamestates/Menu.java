package gamestates;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static utilz.Constants.UI.Buttons.*;

public class Menu extends State implements StateMethods {

    public MenuButton[] buttons = new MenuButton[2];
    public MenuButton[] customButtons = new MenuButton[1];
    public BufferedImage title = LoadSave.GetSpriteAtlas(LoadSave.TITLE);
    public BufferedImage ribbon = LoadSave.GetSpriteAtlas(LoadSave.RIBBON);
    public BufferedImage banner = LoadSave.GetSpriteAtlas(LoadSave.BANNER);

    // MENU DIMENSIONS
    public final int bannerWidth = (int) (400 * SCALE);
    public final int bannerHeight = (int) (550 * SCALE);
    public final int bannerXOffset = bannerWidth / 2;


    public final int ribbonWidth = (int) (500 * SCALE);
    public final int ribbonHeight = (int) (100 * SCALE);
    public final int ribbonXOffset = ribbonWidth / 2;


    public final int titleWidth = (int) (225 * SCALE);
    public final int titleHeight = (int) (35 * SCALE);
    public final int titleXOffset = titleWidth / 2;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadCustomButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (180 * SCALE), 1, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (320 * SCALE), 6, Gamestate.QUIT);
    }

    private void loadCustomButtons() {
        customButtons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (250 * SCALE), 384, 64,
                OPTIONS_BUTTON_WIDTH,
                OPTIONS_BUTTON_HEIGHT,
                OPTIONS_BUTTON_WIDTH_DEFAULT,
                OPTIONS_BUTTON_HEIGHT_DEFAULT,
                Gamestate.OPTIONS);
    }
    public void drawExtras(Graphics g) {
        g.drawImage(banner, Game.GAME_WIDTH / 2 - bannerXOffset, (int) (-30 * SCALE), bannerWidth, bannerHeight, null);
        g.drawImage(ribbon, Game.GAME_WIDTH / 2 - ribbonXOffset, (int) (55 * SCALE), ribbonWidth, ribbonHeight, null);
        g.drawImage(title, Game.GAME_WIDTH / 2 - titleXOffset, (int) (75 * SCALE), titleWidth, titleHeight, null);
    }

    @Override
    public void update() {
        for (MenuButton button : buttons) {
            button.update();
        }
        for (MenuButton customButton : customButtons) {
            customButton.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        drawExtras(g);
        for (MenuButton button : buttons) {
            button.draw(g);
        }
        for (MenuButton customButton : customButtons) {
            customButton.drawCustomButton(g, customButton);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isClickInButton(e, button)) {
                button.mousePressed = true;
                break;
            }
        }
        for (MenuButton custom : customButtons) {
            if (isClickInButton(e, custom)) {
                custom.mousePressed = true;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isClickInButton(e, button)) {
                if (button.mousePressed) {
                    button.applyGamestate();
                }
                break;
            }
        }
        for (MenuButton custom : customButtons) {
            if (isClickInButton(e, custom)) {
                if (custom.mousePressed) {
                    custom.applyGamestate();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton button : buttons) {
            button.resetBools();
        }
        for (MenuButton custom : customButtons) {
            custom.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons) {
            button.mouseOver = false;
        }
        for (MenuButton custom : customButtons) {
            custom.mouseOver = false;
        }
        for (MenuButton button : buttons) {
            if (isClickInButton(e, button)) {
                button.mouseOver = true;
                break;
            }
        }
        for (MenuButton custom : customButtons) {
            if (isClickInButton(e, custom)) {
                custom.mouseOver = true;
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
