package entities;

import gamestates.Playing;
import main.Game;
import ui.StatusBar;
import utilz.LoadSave;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    public Playing playing;
    public BufferedImage[][] animations;
    public BufferedImage IMG_Attack, IMG_Dead, IMG_Doorin, IMG_Doorout,
            IMG_Fall, IMG_Ground, IMG_Hit, IMG_Idle, IMG_Jump, IMG_Run;
    public int animationTick, animationIndex, animationSpeed = 35;
    public int playerAction = DOOR_OUT;
    public boolean moving = false;
    public boolean attacking = false;
    public boolean hit = false;
    public boolean left, up, right, down;
    public float playerSpeed = 0.8f * Game.SCALE;
    float xSpeed;
    public int[][] levelData;
    public boolean attackChecked = false;
    public boolean goingLeft = false;
    public boolean enteringRoom = true;
    public boolean exitingRoom = false;
    public boolean shouldDraw = true;

    //HP
    public final int maxHP = 3;
    public int currHP = maxHP;

    // JUMPING / GRAVITY
    public boolean jump = false;
    public float airSpeed = 0f;
    public float gravity = 0.037f * Game.SCALE;
    public float jumpSpeed = -2.3f * Game.SCALE;
    public float fallSpeedAfterCollision = 0.5f;
    public boolean inAir = false;

    public float xDrawOffset = (int) (22 * Game.SCALE);
    public float yDrawOffset = (int) (20 * Game.SCALE);

    //DASH
    public boolean dashing = false;
    public float dashSpeed = 0.9f * Game.SCALE;

    //ATTACK BOX
    public Rectangle2D.Float attackBox;

    //STATUS BAR UI
    public StatusBar statusBar = new StatusBar(this);

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int) (19 * Game.SCALE), (int) (23 * Game.SCALE));
        initAttackBox();
    }

    public void update() {
        statusBar.update();
        if (currHP <= 0) {
            playing.gameOver = true;
            return;
        }
        updateAttackBox();
        updatePosition();

        if (attacking)
            checkAttack();

        updateAnimationTick();
        setAnimation();
    }

    public void draw(Graphics g, int levelOffset) {
        if (shouldDraw) {
            if (goingLeft)
                g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset - levelOffset + (float) width / 2 + 24 * Game.SCALE),
                        (int) (hitbox.y - yDrawOffset), -width, height, null);
            else
                g.drawImage(animations[playerAction][animationIndex], (int) (hitbox.x - xDrawOffset) - levelOffset,
                        (int) (hitbox.y - yDrawOffset), width, height, null);
        }
//        drawBoxes(g, levelOffset);
        statusBar.draw(g);
    }

    public void drawBoxes(Graphics g, int levelOffset) {
        drawHitbox(g, levelOffset);
        drawAttackBox(g, levelOffset);
    }

    public void drawAttackBox(Graphics g, int levelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - levelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
                attackChecked = false;
                hit = false;
                enteringRoom = false;
                exitingRoom = false;
                if (playerAction == DOOR_IN) {
                    shouldDraw = false;
                    playing.levelManager.loadNextLevel();
                    playing.resetAll();
                }
            }
        }
    }

    public void setAnimation() {

        int startAnimation = playerAction;

        if (enteringRoom) {
            playerAction = DOOR_OUT;
        } else if (exitingRoom) {
            playerAction = DOOR_IN;
        } else if (hit) {
            playerAction = HIT;
        } else if (attacking) {
            playerAction = ATTACK;
        } else if (moving) {
            playerAction = RUNNING;
        } else
            playerAction = IDLE;

        if (inAir) {
            if (attacking)
                playerAction = ATTACK;
            else {
                if (airSpeed < 0) {
                    playerAction = JUMP;
                } else {
                    playerAction = FALL;
                }
            }
        }

        if (startAnimation != playerAction) {
            resetAnimation();
        }

    }

    public void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, 25 * Game.SCALE, 50 * Game.SCALE);
    }

    public void updatePosition() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (left && right))
                return;

        xSpeed = 0;

        if (left) {
            goingLeft = true;
            if (dashing)
                xSpeed -= dashSpeed + playerSpeed;
            else
                xSpeed -= playerSpeed;
        }
        if (right) {
            goingLeft = false;
            if (dashing)
                xSpeed += dashSpeed + playerSpeed;
            else
                xSpeed += playerSpeed;
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

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHP(int amount) {
        currHP += amount;
        if (amount < 0) {
            hit = true;
            statusBar.checkHitHeart = true;
        }
        if (currHP <= 0) {
            currHP = 0;
            //gameOver();
        } else if (currHP > maxHP) {
            currHP = maxHP;
        }
    }

    public void checkAttack() {
        if (attackChecked || animationIndex != 0)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    public BufferedImage createSubImg(BufferedImage image, int row) {
        return image.getSubimage( 78 * row, 0, 78, 58);
    }

    public void updateAttackBox() {
        if (xSpeed >= 0) {
            attackBox.x = hitbox.x + hitbox.width + (int) (10 * Game.SCALE);
        } else {
            attackBox.x = hitbox.x - hitbox.width - (int) (15 * Game.SCALE);
        }
        attackBox.y = hitbox.y - 20 * Game.SCALE ;
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

    // In case the window loses focus this stops the player movement
    public void resetDirectionBool() {
        up = false;
        right = false;
        down = false;
        left = false;
    }

    public void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public void resetAnimation() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void resetAll() {
        resetDirectionBool();
        resetAnimation();
        resetInAir();

        attacking = false;
        hit = false;
        moving = false;
        shouldDraw = true;
        currHP = maxHP;
        hitbox.x = x;
        hitbox.y = y;
        if (IsEntityInAir(hitbox, levelData))
            inAir = true;
        enteringRoom = true;
        playerAction = DOOR_OUT;
    }
}
