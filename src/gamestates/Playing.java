package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static main.Game.SCALE;

public class Playing extends State implements StateMethods {

    public Player player;
    public LevelManager levelManager;
    public EnemyManager enemyManager;
    public boolean paused = false;
    public boolean gameOver = false;
    public PauseOverlay pauseOverlay;
    public GameOverOverlay gameOverOverlay;

    public int xLevelOffset;
    public int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
    public int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
    public int levelTilesWidth = LoadSave.GetLevelData()[0].length;
    public int maxTilesOffset = levelTilesWidth - Game.TILES_IN_WIDTH;
    public int maxLevelOffsetX = maxTilesOffset * Game.TILE_SIZE;

    public Playing(Game game) {
        super(game);
        initializeClasses();
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            checkCloseToBorder();
            levelManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();

        } else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);
        player.draw(g, xLevelOffset);

        if (paused) {
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    public void initializeClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player((int) (200 * SCALE),(int) (200 * SCALE) , (int) (78 * SCALE), (int) (58 * SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    public void unpauseGame() {
        paused = false;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkCloseToBorder() {
        int playerX = (int) player.hitbox.x;
        int diff = playerX - xLevelOffset;

        if (diff > rightBorder) {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLevelOffset += diff - leftBorder;
        }

        if (xLevelOffset > maxLevelOffsetX) {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.attacking = true;
            }
    }

    public void mousePressed(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.KeyPressed(e);
        else
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
                case KeyEvent.VK_K:
                    player.attacking = true;
                    break;
                case KeyEvent.VK_SHIFT:
                    player.dashing = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
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
                case KeyEvent.VK_SHIFT:
                    player.dashing = false;
                    break;
            }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }
}
