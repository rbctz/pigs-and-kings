package main;

public class Game implements Runnable {

    public GameWindow gameWindow;
    public GamePanel gamePanel;
    public Thread gameThread;
    public final int FPS = 120;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double timePerFrame = (double) 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerFrame;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                gamePanel.repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }
        }
    }

}
