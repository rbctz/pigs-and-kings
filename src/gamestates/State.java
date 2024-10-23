package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {

    public Game game;
    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isClickInButton(MouseEvent e, MenuButton button) {
        return button.buttonBounds.contains(e.getX(), e.getY());
    }
}
