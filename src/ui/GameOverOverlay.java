package ui;

import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {

    public Playing playing;
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0, 220));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("GAME OVER!!!!!", Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2);
        g.drawString("PRESS ESC TO GO BACK TO MAIN MENU", Game.GAME_WIDTH / 2 - 50, Game.GAME_HEIGHT / 2 + 50);
    }

    public void KeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
