package entities;

import main.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public abstract class Enemy extends Entity{

    public int animationIndex, enemyState, enemyType;
    public int animationSpeed = 25, animationTick;
    public boolean firstUpdate = true;
    public boolean inAir = false;
    public float fallSpeed;
    public float gravity = 0.04f * Game.SCALE;
    public float xSpeed;
    public float walkSpeed = 0.4f * Game.SCALE;
    public int walkDir;


    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(PIG, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    public void update(int[][] levelData) {
        updateMovement(levelData);
        updateAnimationTick();
    }

    public void updateMovement(int[][] levelData) {
        if (firstUpdate) {
            firstUpdate = false;
            if (IsEntityInAir(hitbox, levelData)) {
                inAir = true;
            }
        }
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUN;
                    break;
                case RUN:
                    xSpeed = 0;
                    if (walkDir == LEFT)
                        xSpeed -= walkSpeed;
                    else
                        xSpeed += walkSpeed;

                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                        if (IsFloor(hitbox, xSpeed, levelData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();
                    break;
            }
        }
    }

    public void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }


}
