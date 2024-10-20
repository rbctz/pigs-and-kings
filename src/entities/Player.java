package entities;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    public BufferedImage[][] animations;
    public BufferedImage IMG_Attack, IMG_Dead, IMG_Doorin, IMG_Doorout,
            IMG_Fall, IMG_Ground, IMG_Hit, IMG_Idle, IMG_Jump, IMG_Run;
    public int animationTick, animationIndex, animationSpeed = 25;
    public int playerAction = IDLE;
    public boolean moving = false;
    public boolean attacking = false;
    public boolean left, up, right, down;
    public float playerSpeed = 0.8f * Game.SCALE;
    public int[][] levelData;

    public float dashSpeed = 1f * Game.SCALE;
    // JUMPING / GRAVITY
    public boolean jump = false;
    public float airSpeed = 0f;
    public float gravity = 0.037f * Game.SCALE;
    public float jumpSpeed = -2.3f * Game.SCALE;
    public float fallSpeedAfterCollision = 0.5f;
    public boolean inAir = false;

    public float xDrawOffset = (int) (22 * Game.SCALE);
    public float yDrawOffset = (int) (20 * Game.SCALE);
    public boolean dashing = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (19 * Game.SCALE), (int) (23 * Game.SCALE));
    }

    // In case the window loses focus this stops the player movement
    public void resetDirectionBool() {
        up = false;
        right = false;
        down = false;
        left = false;
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void draw(Graphics g, int levelOffset) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - levelOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    public void setAnimation() {

        int startAnimation = playerAction;
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALL;
            }
        }

        if (attacking) {
            playerAction = ATTACK;
        }

        if (startAnimation != playerAction) {
            resetAnimation();
        }
    }

    public void resetAnimation() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void updatePosition() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (left && right))
                return;

        float xSpeed = 0;

        if (dashing) {

            if (left)
                xSpeed = -dashSpeed - playerSpeed;
            if (right)
                xSpeed = dashSpeed + playerSpeed;
        } else {
            if (left)
                xSpeed = - playerSpeed;
            if (right)
                xSpeed = playerSpeed;
        }

        if (!inAir) {
            if(IsEntityInAir(hitbox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public BufferedImage createSubImg(BufferedImage image, int row) {
        return image.getSubimage( 78 * row, 0, 78, 58);
    }

    public void loadAnimations() {
        IMG_Attack = LoadSave.GetSpriteAtlas(LoadSave.KING_ATTACK);
        IMG_Dead = LoadSave.GetSpriteAtlas(LoadSave.KING_DEAD);
        IMG_Doorin = LoadSave.GetSpriteAtlas(LoadSave.KING_DOOR_IN);
        IMG_Doorout = LoadSave.GetSpriteAtlas(LoadSave.KING_DOOR_OUT);
        IMG_Fall = LoadSave.GetSpriteAtlas(LoadSave.KING_FALL);
        IMG_Ground = LoadSave.GetSpriteAtlas(LoadSave.KING_GROUND);
        IMG_Hit = LoadSave.GetSpriteAtlas(LoadSave.KING_HIT);
        IMG_Idle = LoadSave.GetSpriteAtlas(LoadSave.KING_IDLE);
        IMG_Jump = LoadSave.GetSpriteAtlas(LoadSave.KING_JUMP);
        IMG_Run = LoadSave.GetSpriteAtlas(LoadSave.KING_RUN);

        animations = new BufferedImage[10][11];
        int i = 0, j;
        for (j = 0; j < 3; j++) animations[i][j] = createSubImg(IMG_Attack, j);
        i++;
        for (j = 0; j < 4; j++) animations[i][j] = createSubImg(IMG_Dead, j);
        i++;
        for (j = 0; j < 8; j++) animations[i][j] = createSubImg(IMG_Doorin, j);
        i++;
        for (j = 0; j < 8; j++) animations[i][j] = createSubImg(IMG_Doorout, j);
        i++;
        animations[i][0] = createSubImg(IMG_Fall, 0);
        i++;
        animations[i][0] = createSubImg(IMG_Ground, 0);
        i++;
        for (j = 0; j < 2; j++) animations[i][j] = createSubImg(IMG_Hit, j);
        i++;
        for (j = 0; j < 11; j++) animations[i][j] = createSubImg(IMG_Idle, j);
        i++;
        animations[i][0] = createSubImg(IMG_Jump, 0);
        i++;
        for (j = 0; j < 8; j++) animations[i][j] = createSubImg(IMG_Run, j);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (IsEntityInAir(hitbox, levelData)) {
            inAir = true;
        }
    }
}
