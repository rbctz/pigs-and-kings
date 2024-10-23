package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import javax.swing.*;
import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    public final MouseInputs mouseInputs = new MouseInputs(this);
    public final KeyboardInputs keyboardInputs = new KeyboardInputs(this);

    public Game game;

    public GamePanel(Game game) {

        this.game = game;
        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setBackground(Color.pink);
    }

    //    attack =  3
    //    dead =    4
    //    doorin =  8
    //    doorout = 8
    //    fall =    1
    //    ground =  1
    //    hit =     2
    //    idle =    11
    //    jump =    1
    //    run =     8

    private void setPanelSize() {
        Dimension screenSize = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(screenSize);
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
