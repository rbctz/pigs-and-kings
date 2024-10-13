package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel {

    private final MouseInputs mouseInputs;
    int xDelta = 0, yDelta = 0;
    public BufferedImage bufferedImage;

    public GamePanel() {

        mouseInputs = new MouseInputs(this);
        importImage();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void importImage() {
        try {
            bufferedImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Idle (78x58).png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPanelSize() {
        Dimension screenSize = new Dimension(1280, 800);
        setPreferredSize(screenSize);
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
    }

    public void changeX(int value) {
        this.xDelta += value;
        repaint();
    }
    public void changeY(int value) {
        this.yDelta += value;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage();
    }


}
