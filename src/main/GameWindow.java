package main;

import javax.swing.*;

public class GameWindow  extends JFrame {

    private JFrame jframe;


    public GameWindow(GamePanel gamePanel) {

        jframe = new JFrame();
        jframe.setSize(800, 600);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setResizable(false);




    }
}
