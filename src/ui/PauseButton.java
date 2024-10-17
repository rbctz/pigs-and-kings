package ui;

import java.awt.*;

public class PauseButton {
    public int x, y, width, height;
    public Rectangle bounds;

    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }


}
