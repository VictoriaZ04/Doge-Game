import java.awt.*;

public class LaserEmitter extends WorldObject {
    private int waitTime;
    private int deployTime;
    private int timer;
    public int direction;//0 = U; 1 = D; 2 = L; 3 = R

    public LaserEmitter(Rectangle item, int waitTime, int deployTime, int direction) {
        super(item);
        this.waitTime = 100;
        this.deployTime = 75;
        timer = 0;
        this.direction = direction;
        type = EMITTER;

    }

    public boolean isCharging(){
        return timer < waitTime && timer > waitTime - 20;
    } 

    public boolean isDeployed(){
        return timer > waitTime;
    }

    public void update(){
        timer++;

        if(timer > waitTime + deployTime){
            timer = 0;
        }
    }

    @Override
    public boolean getCollision(Rectangle rect) {
        

        return super.getCollision(rect);
    }
}
