import java.awt.*;

public class LaserBeam extends Obstacle{
    private boolean isDeployed;
    private double deployTime;
    private double waitTime;
    private int length;
    private int width;
    private int direction; //0 = U; 1 = D; 2 = L; 3 = R
    private int timer;


    public LaserBeam(Rectangle item, int waitTime, int deployTime, int direction) {
        super(item, 0);
        this.length = item.height;
        this.width = item.width;
        this.waitTime = waitTime;
        this.deployTime = deployTime;
        this.direction = direction;
        timer = 0;
    }

    @Override
    public void update() {
        super.update();
        timer++;

        if(timer > waitTime){
            item.setSize(width, length);

        }

        if(timer > waitTime + deployTime){
            timer = 0;
            item.setSize(0,0);
        }
    }

    
}
