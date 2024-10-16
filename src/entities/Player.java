package entities;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    public BufferedImage[][] animations;
    public BufferedImage IMG_Attack, IMG_Dead, IMG_Doorin,IMG_Doorout,
            IMG_Fall, IMG_Ground, IMG_Hit, IMG_Idle, IMG_Jump, IMG_Run;
    public int animationTick, animationIndex, animationSpeed = 20;
    public int playerAction = IDLE;
    public boolean moving = false;
    public boolean attacking = false;
    public boolean left, up, right, down;
    public float playerSpeed = 0.8f * Game.SCALE;
    public int[][] levelData;

    // JUMPING / GRAVITY
    public boolean jump;
    public float airSpeed = 0f;
    public float gravity = 0.037f * Game.SCALE;
    public float jumpSpeed = -2.3f * Game.SCALE;
    public float fallSpeedAfterCollision = 0.5f;
    public boolean inAir = false;

    public float xDrawOffset = (int) (22 * Game.SCALE);
    public float yDrawOffset = (int) (20 * Game.SCALE);

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (19 * Game.SCALE), (int) (23 * Game.SCALE));
    }

    // In case the window loses focus this stops the player movement
    public void resetDirectionBool() {
        setUp(false);
        setDown(false);
        setLeft(false);
        setRight(false);
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void update() {
        setAnimation();
        updatePosition();
        updateAnimationTick();
    }

    public void draw(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
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

        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }
        float xSpeed = 0;
        if (left)
            xSpeed = -playerSpeed;
        if (right)
            xSpeed = playerSpeed;

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
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
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
        try {
            IMG_Attack = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Attack (78x58).png")));
            IMG_Dead = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Dead (78x58).png")));
            IMG_Doorin = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Door In (78x58).png")));
            IMG_Doorout = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Door Out (78x58).png")));
            IMG_Fall = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Fall (78x58).png")));
            IMG_Ground = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Ground (78x58).png")));
            IMG_Hit = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Hit (78x58).png")));
            IMG_Idle = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Idle (78x58).png")));
            IMG_Jump = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Jump (78x58).png")));
            IMG_Run = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Run (78x58).png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
