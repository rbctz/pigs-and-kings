package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    public float x, y;
    public int width, height;
    public Rectangle2D.Float hitbox;
    public Rectangle2D.Float attackBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawHitbox(Graphics g, int levelOffset) {
        g.setColor(Color.black);
        g.drawRect((int) hitbox.x - levelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }
}
