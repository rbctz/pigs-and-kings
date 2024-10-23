package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    public GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.mousePressed(e);
                break;
            case MENU:
                gamePanel.game.menu.mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.mouseReleased(e);
                break;
            case MENU:
                gamePanel.game.menu.mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.mouseDragged(e);
                break;
            case MENU:
            case QUIT:
            case OPTIONS:
            case null, default:
                break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.game.playing.mouseMoved(e);
                break;
            case MENU:
                gamePanel.game.menu.mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
