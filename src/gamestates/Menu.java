package gamestates;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {

    public MenuButton[] buttons = new MenuButton[3];
    public BufferedImage title = LoadSave.GetSpriteAtlas(LoadSave.TITLE);
    public BufferedImage ribbon = LoadSave.GetSpriteAtlas(LoadSave.RIBBON);
    public BufferedImage banner = LoadSave.GetSpriteAtlas(LoadSave.BANNER);


    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 1, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 3, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 6, Gamestate.QUIT);
    }
    public void drawExtras(Graphics g) {
        g.drawImage(banner, Game.GAME_WIDTH / 2 - 440, Game.GAME_HEIGHT / 2 - 450,900, 1000, null);
        g.drawImage(ribbon, Game.GAME_WIDTH / 2 - 440, Game.GAME_HEIGHT / 2 - 325,900, 200, null);
        g.drawImage(title, Game.GAME_WIDTH / 2 - 220, Game.GAME_HEIGHT / 2 - 300,450, 75, null);
    }

    @Override
    public void update() {
        for (MenuButton button : buttons) {
            button.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        drawExtras(g);
        for (MenuButton button : buttons) {
            button.draw(g);
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
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton button : buttons) {
            button.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons) {
            button.mouseOver = false;
        }
        for (MenuButton button : buttons) {
            if (isClickInButton(e, button)) {
                button.mouseOver = true;
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
