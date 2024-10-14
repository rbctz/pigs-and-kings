package main;

public class Game implements Runnable {

    public GameWindow gameWindow;
    public GamePanel gamePanel;
    public Thread gameThread;
    public final int FPS = 120;
    public final int UPS = 200;

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

    public void update() {
        gamePanel.updateGame();
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

}
