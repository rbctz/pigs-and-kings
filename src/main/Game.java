package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import java.awt.*;

public class Game implements Runnable {

    public GameWindow gameWindow;
    public GamePanel gamePanel;
    public Thread gameThread;
    public Menu menu;
    public Playing playing;

    public final static int FPS = 120;
    public final static int UPS = 200;

    public final static int TILE_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = (TILE_SIZE * TILES_IN_WIDTH);
    public final static int GAME_HEIGHT = (TILE_SIZE * TILES_IN_HEIGHT);

    public Game() {
        initializeClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();


        // LAST THING IS ALWAYS START GAME LOOP
        startGameLoop();
    }

    private void initializeClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {


        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {

        final double timePerFrame = (double) 1_000_000_000 / FPS;
        final double timePerUpdate = (double) 1_000_000_000 / UPS;
        double deltaF = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        int updateCount = 0;

        double deltaU = 0;

        while (true) {
            currentTime = System.nanoTime();
            deltaF += ((currentTime - lastTime) / timePerFrame);
            deltaU += ((currentTime - lastTime) / timePerUpdate);
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (deltaF >= 1) {
                gamePanel.repaint();
                drawCount++;
                deltaF--;
            }
            if (deltaU >= 1) {
                update();
                updateCount++;
                deltaU--;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount + " | UPS: " + updateCount);
                timer = 0;
                drawCount = 0;
                updateCount = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.player.resetDirectionBool();
    }
}
