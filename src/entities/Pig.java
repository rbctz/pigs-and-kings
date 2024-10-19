package entities;

import static utilz.Constants.EnemyConstants.*;

public class Pig extends Enemy {

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT, PIG);
    }
}
