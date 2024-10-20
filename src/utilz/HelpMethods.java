package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {

    static int[] solidTiles = {20, 21, 22, 24, 26, 27, 29, 30,
            32, 33, 34, 39, 40, 41, 43, 45, 46, 48, 49, 51, 52, 54,
            55, 58, 59, 60, 62, 83, 84, 86, 87, 89, 90, 92, 93, 96, 97,
            98, 100, 102, 103, 105, 106, 108, 109, 111, 112};

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {

        if(IsTransparent(x, y, levelData))
            if (IsTransparent(x + width, y, levelData))
                if(IsTransparent(x, y + height, levelData))
                    if(IsTransparent(x + width, y + height, levelData))
                        return true;
        return false;
    }

    public static boolean IsTransparent(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth || y < 0 || y >= Game.GAME_HEIGHT) {
            return false;
        }
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex];

        for (int i = 0; i < solidTiles.length; i++) {
            if (solidTiles[i] == value) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth || y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        return IsSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];

        for (int i = 0; i < solidTiles.length; i++) {
            if (solidTiles[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) hitbox.x / Game.TILE_SIZE;
        if (xSpeed > 0) {
            //right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            //left
            return currentTile * Game.TILE_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airspeed) {
        int currentTile = (int) hitbox.y / Game.TILE_SIZE;
        if (airspeed > 0) {
            //FALLING
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //JUMPING
            return currentTile * Game.TILE_SIZE;
        }
    }

    public static boolean IsEntityInAir(Rectangle2D.Float hitbox, int[][] levelData) {
        //CHECK PIXELS ON BOTTOM CORNERS

        float yOffset = hitbox.y + hitbox.height + 1;
        if (yOffset != (int) (yOffset)) {
            yOffset = (int) (yOffset) + 1;
        }
        return IsTransparent(hitbox.x, yOffset, levelData) && IsTransparent(hitbox.x + hitbox.width, yOffset, levelData);
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox,float xSpeed, int[][] levelData) {
        return !IsTransparent(hitbox.x + xSpeed + hitbox.width / 2, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, levelData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int yTile, int[][] levelData) {

        int xTile1 = (int) (hitbox1.x / Game.TILE_SIZE);
        int xTile2 = (int) (hitbox2.x / Game.TILE_SIZE);

        if (xTile1 > xTile2)
            return IsAllTilesWalkable(xTile2, xTile1, yTile, levelData);
        else
            return IsAllTilesWalkable(xTile1, xTile2, yTile, levelData);
    }
}
