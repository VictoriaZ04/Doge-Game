import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Panel extends JPanel implements KeyListener,Runnable  { //,Runnable
    BufferedImage buffer = null;
    private int updatesPerSecond = 50;
    private long startTime = System.nanoTime();
    private int updateCount = 0;
    int width = 120;
    int count = 0;
    int change = 0;
    World w;
    boolean left;
    boolean right;
    boolean up;
    boolean down;
    int pX;
    int pY ;
    private BufferedImage dungeonWall = null;
    private BufferedImage dogeRight = null;
    private BufferedImage dogeLeft = null;
    private BufferedImage dogeHeart = null;
    private BufferedImage LaserDown1 = null;
    private BufferedImage LaserDown2 = null;
    private BufferedImage LaserDown3 = null;
    private BufferedImage LaserUp1 = null;
    private BufferedImage LaserUp2 = null;
    private BufferedImage LaserUp3 = null;
    private BufferedImage LaserRight1 = null;
    private BufferedImage LaserRight2 = null;
    private BufferedImage LaserRight3 = null;
    private BufferedImage LaserLeft1 = null;
    private BufferedImage LaserLeft2 = null;
    private BufferedImage LaserLeft3 = null;

    private int counter = 0;

    public Panel() throws IOException {
        super();
        setSize(250, 250);
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        addKeyListener(this);
        Thread t = new Thread(this);

        w = new World();
        w.changeLevel(5);
        pX = (int) w.getPlayer().getRect().getX();
        pY =  (int) w.getPlayer().getRect().getY();
        dungeonWall = ImageIO.read((new File("DogeImages/dungeonWall.png")));
        dogeRight = ImageIO.read((new File("DogeImages/dogeRight.png")));
        dogeLeft = ImageIO.read((new File("DogeImages/dogeLeft.png")));
        dogeHeart = ImageIO.read((new File("DogeImages/dogeHeart.png")));
        LaserDown1 = ImageIO.read((new File("DogeImages/LaserDown1.png")));
        LaserDown2 =ImageIO.read((new File("DogeImages/LaserDown2.png")));
        LaserDown3 =ImageIO.read((new File("DogeImages/LaserDown3.png")));
        LaserUp1 =ImageIO.read((new File("DogeImages/LaserUp1.png")));
        LaserUp2 =ImageIO.read((new File("DogeImages/LaserUp2.png")));
        LaserUp3 =ImageIO.read((new File("DogeImages/LaserUp1.png")));
        LaserLeft1 =ImageIO.read((new File("DogeImages/LaserLeft1.png")));
        LaserLeft2 =ImageIO.read((new File("DogeImages/LaserLeft2.png")));
        LaserLeft3 =ImageIO.read((new File("DogeImages/LaserLeft3.png")));
        LaserRight1 =ImageIO.read((new File("DogeImages/LaserRight1.png")));
        LaserRight2 =ImageIO.read((new File("DogeImages/LaserRight2.png")));
        LaserRight3 =ImageIO.read((new File("DogeImages/LaserRight3.png")));


        t.start();

    }

    public void paint(Graphics g)  {

        Graphics bg = buffer.getGraphics();

        // int pixLRP = 120; //sw - pw    (250 - 10)/2
        // int pixABP = 120; // sh - ph
        pX = (int) w.getPlayer().getRect().getX();
        pY =  (int) w.getPlayer().getRect().getY();
        int maxX = w.getBoard()[0].length  * 10 - 250; //ww - sw
        int maxY =  w.getBoard().length * 10 - 250; //wh - sh
        int tempX = (int) pX - 120;
        int tempY = (int) pY- 120;
        int sx = (tempX < 0)? 0:(tempX > maxX)? maxX :tempX;
        int sy = (tempY < 0)? 0:(tempY > maxY)? maxY :tempY;
        int startR = sy/10; //0
        int startC = sx/10; //
        int endR =  (startR == 0)? 25:startR+25;
        int endC = (startC == 0)? 25:startC+25;

        bg.setColor(new Color(184, 252, 182));
        bg.fillRect(0, 0, 250, 250);
        for(int r = 0; r < w.getBoard().length; r++){
            for(int c = 0; c < w.getBoard()[0].length; c++){
                if(w.getBoard()[r][c] != null && w.getBoard()[r][c].getCollision(new Rectangle(sx, sy, w.getBoard()[0].length * 10, w.getBoard().length * 10))){
                    if (w.getBoard()[r][c].getType() == WorldObject.WALL) {
                        bg.drawImage(dungeonWall,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if (w.getBoard()[r][c].getType() == WorldObject.FINISH) {
                        bg.setColor(new Color(6, 255, 0));
                        bg.fillRect((int) Math.abs(sx-(c*10)),(int) Math.abs(sy-(r*10)),10,10);
                    }
                    if (w.getBoard()[r][c].getType() == WorldObject.EMITTER && ((LaserEmitter) w.getBoard()[r][c]).direction == 2) {
                        if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                            bg.drawImage(LaserLeft2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                            bg.drawImage(LaserLeft3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else
                        bg.drawImage(LaserLeft1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);

                    }
                    else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER && ((LaserEmitter) w.getBoard()[r][c]).direction == 0) {
                        if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                            bg.drawImage(LaserUp2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                            bg.drawImage(LaserUp3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else
                        bg.drawImage(LaserUp1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER  && ((LaserEmitter) w.getBoard()[r][c]).direction == 3) {
                        if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                            bg.drawImage(LaserRight2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                            bg.drawImage(LaserRight3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else
                        bg.drawImage(LaserRight1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER  && ((LaserEmitter) w.getBoard()[r][c]).direction == 1) {
                        if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                            bg.drawImage(LaserDown2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                            bg.drawImage(LaserDown3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                        }
                        else
                        bg.drawImage(LaserRight3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                }
            }
        }
        for(int c = startC; c < endC;c++) {
            for (int r = startR; r < endR; r++) {
                if (w.getBoard()[r][c] == null) {
                    bg.setColor(new Color(184, 252, 182));
                    bg.fillRect((int) Math.abs(sx - (c * 10)), (int) Math.abs(sy - (r * 10)), 10, 10);
                } else if (w.getBoard()[r][c].getType() == WorldObject.WALL) {
                    bg.drawImage(dungeonWall, (int) Math.abs(sx - w.getBoard()[r][c].getRect().getX()), (int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()), null);
                } else if (w.getBoard()[r][c].getType() == WorldObject.FINISH) {
                    bg.setColor(new Color(6, 255, 0));
                    bg.fillRect((int) Math.abs(sx - (c * 10)), (int) Math.abs(sy - (r * 10)), 10, 10);
                }
                else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER  && ((LaserEmitter) w.getBoard()[r][c]).direction == 2) {
                    if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                        bg.drawImage(LaserLeft2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                        bg.drawImage(LaserLeft3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    bg.drawImage(LaserLeft1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);

                }
                else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER && ((LaserEmitter) w.getBoard()[r][c]).direction == 0) {
                    if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                        bg.drawImage(LaserUp2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                        bg.drawImage(LaserUp3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else
                        bg.drawImage(LaserUp1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                }
                else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER  && ((LaserEmitter) w.getBoard()[r][c]).direction == 3) {
                    if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                        bg.drawImage(LaserRight2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                        bg.drawImage(LaserRight3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else
                        bg.drawImage(LaserRight1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                }
                else if (w.getBoard()[r][c].getType() == WorldObject.EMITTER  && ((LaserEmitter) w.getBoard()[r][c]).direction == 1) {
                    if(((LaserEmitter) w.getBoard()[r][c]).isCharging()){
                        bg.drawImage(LaserDown2,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else if(((LaserEmitter) w.getBoard()[r][c]).isDeployed()){
                        bg.drawImage(LaserDown3,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                    }
                    else
                        bg.drawImage(LaserDown1,(int)Math.abs(sx - w.getBoard()[r][c].getRect().getX()),(int) Math.abs(sy - w.getBoard()[r][c].getRect().getY()),null);
                }
            }
        }
        for (int x = 0; x < w.getObstacles().size(); x++) {
            Rectangle obs = w.getObstacles().get(x).getRect();
            
            if((int)(obs.getX() / 10) >= startC && (int)(obs.getX() / 10) < endC && (int)(obs.getY() / 10) >= startR && (int)(obs.getY() / 10) < endR)
            {
                bg.setColor(new Color(255, 0, 0));
                // bg.fillRect((int) Math.abs(sx-w.getObstacles().get(x).getRect().getY()), (int) Math.abs(sy-w.getObstacles().get(x).getRect().getX()),10,10);

                bg.fillRect((int) Math.abs(sx - w.getObstacles().get(x).getRect().getX()), (int)Math.abs(sy - w.getObstacles().get(x).getRect().getY()), (int)w.getObstacles().get(x).getRect().getWidth(),(int)w.getObstacles().get(x).getRect().getHeight());
                //  System.out.print((int) Math.abs(sx - w.getObstacles().get(x).getRect().getY()) + ", " + (int)Math.abs(sy - w.getObstacles().get(x).getRect().getX()));

            } else if(w.getObstacles().get(x).getCollision(new Rectangle(sx, sy, 250,250)) && (w.getObstacles().get(x) instanceof LaserBeam)){
                bg.setColor(new Color(255, 0, 0));
                // int tempx = (int) Math.abs(sx - w.getObstacles().get(x).getRect().getX());
                // int tempy = (int)Math.abs(sy - w.getObstacles().get(x).getRect().getY());
                // int tempw = (int)(w.getObstacles().get(x).getRect().getWidth());
                // int temph = (int)(w.getObstacles().get(x).getRect().getHeight());

                // if(sx - w.getObstacles().get(x).getRect().getX() < 0){
                //     tempx = 0;
                //     tempw -= (int) Math.abs(sx - w.getObstacles().get(x).getRect().getX());
                // }
                // else if(sy - w.getObstacles().get(x).getRect().getY() < 0){
                //     tempy = 0;
                //     temph -= (int)Math.abs(sy - w.getObstacles().get(x).getRect().getY());
                // } 
                // bg.fillRect(tempx, tempy, tempw,temph);
                bg.fillRect((int) (sx - w.getObstacles().get(x).getRect().getX()), (int)(sy - w.getObstacles().get(x).getRect().getY()), (int)w.getObstacles().get(x).getRect().getWidth(),(int)w.getObstacles().get(x).getRect().getHeight());


            }
        }
        for(int c = startC; c < endC;c++) {
            for (int r = startR; r < endR; r++) {

                // for (int x = 0; x < w.getObstacles().size(); x++) {
                if (Math.abs(pX) / 10 == c && Math.abs(pY) / 10 == r) {
                    if (!left)
                        bg.drawImage(dogeRight, Math.abs(pX - sx), Math.abs(pY - sy), null);
                    else if (!right) {
                        bg.drawImage(dogeLeft, Math.abs(pX - sx), Math.abs(pY - sy), null);
                    } else
                        bg.drawImage(dogeRight, Math.abs(pX - sx), Math.abs(pY - sy), null);

                }
                if (w.getBoard()[r][c] != null && w.getBoard()[r][c].getType() == WorldObject.WALL) {
                    if((w.getPlayer().getRect().intersects(w.getBoard()[r][c].getRect()) && left) ||  w.getPlayer().getRect().x <= 0){
                        w.getPlayer().rightUpdate();
                        left = false;
                    } if((w.getPlayer().getRect().intersects(w.getBoard()[r][c].getRect()) && right) || w.getPlayer().getRect().x >= w.getBoard()[0].length*10-20){
                        w.getPlayer().leftUpdate();
                        right = false;
                    } if((w.getPlayer().getRect().intersects(w.getBoard()[r][c].getRect()) && up) ||  w.getPlayer().getRect().y <= 0){
                        w.getPlayer().downUpdate();
                        up = false;
                    } if((w.getPlayer().getRect().intersects(w.getBoard()[r][c].getRect()) && down) || w.getPlayer().getRect().y >= w.getBoard().length*10-20){
                        w.getPlayer().upUpdate();
                        down = false;
                    }
                }
                if(w.getPlayer().lives >= 1){
                    bg.drawImage(dogeHeart,150,5,null);
                }
                if (w.getPlayer().lives == 0){
                    bg.setColor(new Color(255, 0, 0));
                    bg.drawString("Doge is Sad, Press 'r' to Try Again", 25, 105);
                }
                if (w.getLevel() >= 6){
                    bg.setColor(new Color(255, 0, 0));
                    bg.drawString("Doge is Happy, Press 'r' to Reset", 25, 105);
                }

                if(w.getPlayer().lives >= 2)  {
                    bg.drawImage(dogeHeart,170,5,null);
                }
                if(w.getPlayer().lives >= 3) {
                    bg.drawImage(dogeHeart,190,5,null);
                }
                if(w.getPlayer().lives >= 4) {
                    bg.drawImage(dogeHeart,210,5,null);
                }
                if(w.getPlayer().lives >= 5) {
                    bg.drawImage(dogeHeart,230,5,null);
                }


                //}
            }
        }
        // bg.drawOval(50, 50, 200, 200);

        //  g.setColor(Color.green);
        //   g.fillRect(pX,pY,10,10);


        g.drawImage(buffer, 0, 0, null);

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        char dir = e.getKeyChar();
        if(w.getLevel() < 6 && w.getPlayer().lives != 0) {
            if (dir == 'w') {
                up = true;

            }

            if (dir == 'a') {
                left = true;
            }

            if (dir == 's') {
                down = true;
            }

            if (dir == 'd') {
                right = true;
            }
        }

        if(dir == '+'){
            w.getPlayer().setLives(w.getPlayer().lives + 1);
        }

        if((w.getPlayer().lives == 0 || w.getLevel() > 5)&& dir == 'r' || w.getLevel() > 5){
            try {
                w.changeLevel(1);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            reset();
            w.getPlayer().setLives(5);

        }


        //w.getPlayer().update(left, right, down, up);



    }

    public void keyReleased(KeyEvent e) {
        up = false;
        left = false;
        right = false;
        down = false;

    }

    public void reset() {
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    @Override
    public void run() {
        while (true) {
           if (w.getPlayer().lives != 0 && w.getLevel() < 6) {
                ;
                // w.update();
                //   if(w.getPlayer().getRect().intersects())
                if (down) {
                    w.getPlayer().downUpdate();
                }
                if (up) {
                    w.getPlayer().upUpdate();
                }
                if (right) {
                    w.getPlayer().rightUpdate();
                }
                if (left) {
                    w.getPlayer().leftUpdate();
                }
            }
            repaint();
            try {
                Thread.sleep(7);
                repaint();
                counter++;
                if (counter > 5) {
                    counter = 0;
                    w.update();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


}

