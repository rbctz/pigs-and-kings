package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class GamePanel extends JPanel {

    public final MouseInputs mouseInputs = new MouseInputs(this);
    public final KeyboardInputs keyboardInputs = new KeyboardInputs(this);
    int xDelta = 0, yDelta = 0;
    public BufferedImage IMG_Attack, IMG_Dead, IMG_Doorin,IMG_Doorout,
            IMG_Fall, IMG_Ground, IMG_Hit, IMG_Idle, IMG_Jump, IMG_Run;
    public BufferedImage[][] animations;
    public int animationTick, animationIndex, animationSpeed = 20;


    public int playerAction = RUNNING;
    public int playerDirection = -1;
    public boolean moving = false;


    public GamePanel() {

        importImages();
        loadAnimations();
        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    //    attack =  3
    //    dead =    4
    //    doorin =  8
    //    doorout = 8
    //    fall =    1
    //    ground =  1
    //    hit =     2
    //    idle =    11
    //    jump =    1
    //    run =     8
    private void loadAnimations() {
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

    public void importImages() {
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
        } finally {
            try {
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("Attack (78x58).png")).close();
            } catch (IOException e) {

            }
        }
    }

    public BufferedImage createSubImg(BufferedImage image, int row) {
        return image.getSubimage( 78 * row, 0, 78, 58);
    }

    private void setPanelSize() {
        Dimension screenSize = new Dimension(1280, 800);
        setPreferredSize(screenSize);
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
    }

    public void setDirection(int direction) {
        playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    public void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    public void updatePosition() {
        if (moving) {
            switch (playerDirection) {
                case UP:
                    yDelta -= 3;
                    break;
                case DOWN:
                    yDelta += 3;
                    break;
                case LEFT:
                    xDelta -= 3;
                    break;
                case RIGHT:
                    xDelta += 3;
                    break;
            }
        }
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animations[playerAction][animationIndex], xDelta, yDelta, 312, 232, this);

    }



}
