import java.awt.*;

public class LObstacle extends Obstacle{
    private Point pStart;
    private Point pTurn;
    private Point pEnd;
    public LObstacle(Rectangle item, double speed, Point pStart, Point pTurn, Point pEnd){
        super(item, speed);
        this.pEnd = pEnd;
        this.pStart = pStart;
        this.pTurn = pTurn;
    }

    @Override
    public void update() {
        super.update();

        if(item.getLocation().equals(pStart) ){
            speed = 1;
        }
        else if (item.getLocation().equals(pEnd)){
            speed = -1;
        }
        if(speed > 0){
            if((item.getX() == pTurn.getX() && item.getX() == pStart.getX() && item.getY() != pTurn.getY())){
                // Start -> turn; vertical
                if(pTurn.getY() > pStart.getY()){
                    item.setLocation((int) item.getX(), (int)(item.getY() + Math.abs(speed)));
                } else if(pTurn.getY() < pStart.getY()){
                    item.setLocation((int) item.getX(), (int)(item.getY() - Math.abs(speed)));
                }
            } else if((item.getY() == pTurn.getY() && item.getY() == pStart.getY() && item.getX() != pTurn.getX())){
                // Start -> turn; vertical

                if(pTurn.getX() > pStart.getX()){
                    item.setLocation((int) (item.getX() + Math.abs(speed)), (int)(item.getY()));
                } else if(pTurn.getX() < pStart.getX()){
                    item.setLocation((int) (item.getX() - Math.abs(speed)), (int)(item.getY()));
                }
            } else if((item.getY() == pEnd.getY())){
                // Start -> turn; horizontal

                if(pTurn.getX() > pEnd.getX()){
                    item.setLocation( (int)(item.getX() - Math.abs(speed)), (int) item.getY());
                } else if(pTurn.getX() < pEnd.getX()){
                    item.setLocation((int)(item.getX() + Math.abs(speed)), (int) item.getY());
                }
            } else {

                if(pTurn.getY() > pEnd.getY()){
                    item.setLocation( (int)(item.getX()), (int) (item.getY() - Math.abs(speed)));
                } else if(pTurn.getY() < pEnd.getY()){
                    item.setLocation((int)(item.getX()), (int) (item.getY() + Math.abs(speed)));
                }
            }
        }
        else if(speed < 0){
            if((item.getX() == pTurn.getX() && item.getX() == pEnd.getX() && item.getY() != pTurn.getY())){
                // Start -> turn; vertical
                if(pTurn.getY() > pEnd.getY()){
                    item.setLocation((int) item.getX(), (int)(item.getY() + Math.abs(speed)));
                } else if(pTurn.getY() < pEnd.getY()){
                    item.setLocation((int) item.getX(), (int)(item.getY() - Math.abs(speed)));
                }
            } else if((item.getY() == pTurn.getY() && item.getY() == pEnd.getY() && item.getX() != pTurn.getX())){
                // Start -> turn; vertical
                if(pTurn.getX() > pEnd.getX()){
                    item.setLocation((int) (item.getX() + Math.abs(speed)), (int)(item.getY()));
                } else if(pTurn.getX() < pEnd.getX()){
                    item.setLocation((int) (item.getX() - Math.abs(speed)), (int)(item.getY()));
                }
            } else if((item.getY() == pStart.getY())){
                // Start -> turn; horizontal
                if(pTurn.getX() > pStart.getX()){
                    item.setLocation( (int)(item.getX() - Math.abs(speed)), (int) item.getY());
                } else if(pTurn.getX() < pStart.getX()){
                    item.setLocation((int)(item.getX() + Math.abs(speed)), (int) item.getY());
                }
            } else {
                if(pTurn.getY() > pStart.getY()){
                    item.setLocation( (int)(item.getX()), (int) (item.getY() - Math.abs(speed)));
                } else if(pTurn.getY() < pStart.getY()){
                    item.setLocation((int)(item.getX()), (int) (item.getY() + Math.abs(speed)));
                }
            }
        
        }
    }
}
