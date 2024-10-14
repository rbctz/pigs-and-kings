package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int ATTACK = 0;
        public static final int DEAD = 1;
        public static final int DOOR_IN = 2;
        public static final int DOOR_OUT = 3;
        public static final int FALL = 4;
        public static final int GROUND = 5;
        public static final int HIT = 6;
        public static final int IDLE = 7;
        public static final int JUMP = 8;
        public static final int RUNNING = 9;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case IDLE:
                    return 11;
                case DOOR_IN:
                case DOOR_OUT:
                case RUNNING:
                    return 8;
                case DEAD:
                    return 4;
                case ATTACK:
                    return 3;
                case HIT:
                    return 2;
                case FALL:
                case GROUND:
                case JUMP:
                default:
                    return 1;
            }
        }
    }
}
