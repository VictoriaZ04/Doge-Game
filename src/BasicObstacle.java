import java.awt.*;

public class BasicObstacle extends Obstacle{
    private boolean isUp;
    public BasicObstacle(Rectangle item, double speed, boolean isUp) {
        super(item, speed);
        this.isUp = isUp;
        type = OBSTACLE;
    }

    @Override
    public void update() {
        super.update();

        if(isUp){
            item.setLocation((int)(item.getX()), (int)(item.getY() + speed));
        } else if(!isUp){
            item.setLocation((int)(item.getX() + speed), (int)(item.getY()));
        }
    }

    @Override
    public boolean getCollision(Rectangle rect){
        boolean ret = super.getCollision(rect);

        if(ret){
            speed *= -1;
            update();
        }
        return ret;
    }
}
