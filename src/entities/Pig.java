package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;

public class Pig extends Enemy {

    public Rectangle2D.Float attackBox;

    public int attackBoxOffsetX;

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT, PIG);
        initHitbox(x, y, (int) (14 * Game.SCALE), (int) (17 * Game.SCALE));
        initAttackBox();
    }

    public void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (8 * Game.SCALE), (int) (24 * Game.SCALE));
        attackBoxOffsetX = (int) (18 * Game.SCALE);
    }

    public void updateAttackBox() {
        if (xSpeed <= 0)
            attackBox.x = hitbox.x - (float) attackBoxOffsetX / 1.5f;
        else
            attackBox.x = hitbox.x + attackBoxOffsetX;
        attackBox.y = hitbox.y - 10 * Game.SCALE;
    }

    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    public void drawAttackBox(Graphics g, int levelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - levelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void updateBehavior(int[][] levelData, Player player) {
        firstUpdateCheck(levelData);

        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUN;
                    break;
                case RUN:
                    if (canSeePlayer(levelData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerInAttackRange(player))
                        newState(ATTACK);
                    move(levelData);
                    break;
                case ATTACK:
                    if (animationIndex == 0)
                        attackChecked = false;
                    if (animationIndex == 2 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;
                case DEAD:
                    break;

            }
        }
    }
}
