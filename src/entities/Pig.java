package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Pig extends Enemy {

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT, PIG);
        initHitbox(x, y, (int) (14 * Game.SCALE), (int) (17 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateMovement(levelData, player);
        updateAnimationTick();
    }

    public void updateMovement(int[][] levelData, Player player) {
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
            }
        }
    }
}
