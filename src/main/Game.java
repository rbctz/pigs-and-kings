package main;

public class Game {

    GameWindow gameWindow;
    private GamePanel gamePanel;

    public Game() {
        gamePanel = new GamePanel();

        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();


    }
}
