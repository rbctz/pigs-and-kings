package entities;

import static utilz.Constants.EnemyConstans.*;

public abstract class Enemy extends Entity{

    public int animationIndex, enemyState, enemyType;
    public int animationSpeed = 25, animationTick;


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

    public void update() {
        updateAnimationTick();
    }


}
