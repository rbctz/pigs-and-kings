package utilz;

import main.Game;

public class HelpMethods {

    static int[] solidTiles = {20, 21, 22, 24, 26, 27, 29, 30,
            32, 33, 34, 39, 40, 41, 43, 45, 46, 48, 49, 51, 52, 54,
            55, 58, 59, 60, 62, 83, 84, 86, 87, 89, 90, 92, 93, 96, 97,
            98, 100, 102, 103, 105, 106, 108, 109, 111, 112};

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {

        if(!IsSolid(x, y, levelData))
            if (!IsSolid(x + width, y, levelData))
                if(!IsSolid(x, y + height, levelData))
                    if(!IsSolid(x + width, y + height, levelData))
                        return true;
        return false;
    }

    public static boolean IsSolid(float x, float y, int[][] levelData) {
        if (x < 0 || x >= Game.GAME_WIDTH || y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        for (int i = 0; i < solidTiles.length; i++) {
            if (solidTiles[i] == value) {
                return true;
            }
        }
        return false;
    }


}
