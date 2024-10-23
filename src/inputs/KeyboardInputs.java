package inputs;


import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {

    GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.keyPressed(e);
                break;
            case MENU:
                gamePanel.game.menu.keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.keyReleased(e);
                break;
            case MENU:
                gamePanel.game.menu.keyReleased(e);
                break;
            default:
                break;
        }
    }
}
