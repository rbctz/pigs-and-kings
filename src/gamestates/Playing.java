package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.SCALE;

public class Playing extends State implements StateMethods {

    public Player player;
    public LevelManager levelManager;
    public boolean paused = false;
    public PauseOverlay pauseOverlay;

    public Playing(Game game) {
        super(game);
        initializeClasses();
    }

    private void initializeClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (78 * SCALE), (int) (58 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    public void windowFocusLost() {
        player.resetDirectionBool();
    }

    public void unpauseGame() {
        paused = false;
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
        } else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.draw(g);
        if (paused) {
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.attacking = true;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.left = true;
                break;
            case KeyEvent.VK_D:
                player.right = true;
                break;
            case KeyEvent.VK_SPACE:
                player.jump = true;
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.left = false;
                break;
            case KeyEvent.VK_D:
                player.right = false;
                break;
            case KeyEvent.VK_SPACE:
                player.jump = false;
                break;
        }
    }
}
