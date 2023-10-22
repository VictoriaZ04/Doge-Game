import org.w3c.dom.css.Rect;

import java.awt.*;

public class Player extends WorldObject {
    public int lives;
    public double x, y;
    private Rectangle item;

    public Player(Rectangle item) {
        super(item);
        this.item = item;
        lives = 5;
        x = item.getX();
        y = item.getY();
        //  item.y = (int) getRect().getY() +10;
        // System.out.print( item.y);

    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void downUpdate() {
        item.y = (int) getRect().getY() +1;
    }
    public void upUpdate()
    {
        item.y = (int) getRect().getY() -1;

    }
    public void leftUpdate() {
        item.x = (int) getRect().getX() -1;

    }
    public void rightUpdate() {
        item.x = (int) getRect().getX() +1; //"x" because "this.item" is null

    }
    @Override
    public boolean getCollision(Rectangle rect){
        return item.intersects(rect);
    }
}

