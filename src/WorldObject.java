import java.awt.*;

public class WorldObject
{
    public Rectangle item;
    public static final int WALL = 4;
    public static final int FINISH = 0;
    public static final int OBSTACLE = 1;
    public static final int EMITTER = 2;
    public static final int PLAYER = 3;
    public int type;

    public WorldObject(Rectangle item) {
        this.item = item;
    }

    public Rectangle getRect() {
        return item;
    }

    public void setRect(Rectangle item) {
        this.item = item;
    }
    public int getType(){
        return type;
    }
    public boolean getCollision(Rectangle rect){
        return item.intersects(rect);
        // return false;
    }
}
