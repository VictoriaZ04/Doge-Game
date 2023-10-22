import java.awt.*;

public class CircleObstacle extends Obstacle{
    private Point center;
    private double radius;
    private boolean isClockwise;

    public CircleObstacle(Rectangle item, double speed, Point center, double radius) {
        super(item, speed);
        this.center = center;
        this.radius = radius;
        x = item.getX() - (item.getWidth() / 2);
        y = item.getY() - (item.getHeight() / 2);

    }

    @Override
    public void update() {
        super.update();
        double angle;

        
        if((x - center.getX()) == 0){
            angle = (y - center.getY() < 0)?Math.PI/2:3*Math.PI/2;
        } else{
            angle = Math.atan((center.getY() - y) / (x - center.getX()));
        }
        angle += .1;
        if(x <= center.getX()){
            x = (center.getX() - (Math.cos(angle) * radius));
            y = ((Math.sin(angle) * radius) + center.getY());
        } else{
            x = ((Math.cos(angle) * radius) + center.getX());
            y = (center.getY() - (Math.sin(angle) * radius));
        }
        item.setLocation((int) x - 5, (int) y - 5);

    }
}
