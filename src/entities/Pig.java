package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Pig extends Enemy {

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT, PIG);
        initHitbox(x, y, (int) (14 * Game.SCALE), (int) (17 * Game.SCALE));
    }
}
