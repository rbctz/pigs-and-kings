package utilz;

import main.Game;

public class Constants {

    public static class UI {
        public static class MenuButtons {
            public static final int B_WIDTH_DEFAULT = 64;
            public static final int B_HEIGHT_DEFAULT = 32;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * 1.5);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * 1.5);
            public static final int OPTIONS_BUTTON_WIDTH_DEFAULT = 80;
            public static final int OPTIONS_BUTTON_HEIGHT_DEFAULT = 32;
            public static final int OPTIONS_BUTTON_WIDTH = (int) (OPTIONS_BUTTON_WIDTH_DEFAULT * Game.SCALE * 1.5);
            public static final int OPTIONS_BUTTON_HEIGHT = (int) (OPTIONS_BUTTON_HEIGHT_DEFAULT * Game.SCALE * 1.5);
        }

        public static class PauseButtons {
            public static final int  SOUND_SIZE_DEFAULT = 42;
            public static final int  SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE * 1.2);
        }

        public static class UrmButtons {
            public static final int  URM_SIZE_DEFAULT = 56;
            public static final int  URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE * 1.2);
        }

        public static class VolumeButtons {
            public static final int  VOLUME_WIDTH_DEFAULT = 28;
            public static final int  VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * Game.SCALE * 1.1);
            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE * 1.1);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE * 1.1);
        }

        public static class StatusBar {
            public static final int STATUSBAR_X = (int) (10 * Game.SCALE);
            public static final int STATUSBAR_Y = (int) (10 * Game.SCALE);
            public static final int STATUSBAR_WIDTH = (int) (66 * Game.SCALE * 1.5);
            public static final int STATUSBAR_HEIGHT = (int) (34 * Game.SCALE * 1.5);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class EnemyConstants {
        public static final int PIG = 100;

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int PIG_WIDTH_DEFAULT = 34;
        public static final int PIG_HEIGHT_DEFAULT = 28;
        public static final int PIG_WIDTH = (int) (PIG_HEIGHT_DEFAULT * Game.SCALE);
        public static final int PIG_HEIGHT = (int) (PIG_HEIGHT_DEFAULT * Game.SCALE);

        public static final int PIG_X_OFFSET = (int) (11 * Game.SCALE);
        public static final int PIG_Y_OFFSET = (int) (11 * Game.SCALE);

        public static int GetSpriteAmount(int enemyType, int enemyState) {
            if (enemyType == PIG) {
                switch (enemyState) {
                    case IDLE:
                        return 11;
                    case RUN:
                        return 6;
                    case ATTACK:
                        return 5;
                    case HIT:
                        return 2;
                    case DEAD:
                        return 4;
                }
            }
            return 0;
        }
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
