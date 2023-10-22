import java.awt.*;

public class Obstacle extends WorldObject{
    public double speed;
    public double x, y;
    public Obstacle(Rectangle item, double speed) {
        super(item);
        this.speed = speed;
        this.x = item.getX();
        this.y = item.getY();
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }
    public void update(){
        
    }
}
