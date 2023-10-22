import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;


public class World {
    private WorldObject[][] board;
    private ArrayList<WorldObject> obstacles;
    private int level = 0;
    private Player player;

    /*int playerRow;
    int playerCol;
    int worldRow;
    int worldCol;
    int displayRows;
    int displayCols;
    for(int r=worldRow; r<worldRow+displayRows;r++)
            for(int c=worldCol; c<worldColumn+displayCols; c++)
    {
        int y = (r-worldRow)*cellHeight;
        int x = (c-worldCol)*cellWidth;
        // code to draw the item
    }
     */


    public World() {
    }

    public WorldObject[][] getBoard() {
        return board;
    }

    public void setBoard(WorldObject[][] board) {
        this.board = board;
    }

    public ArrayList<WorldObject> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<WorldObject> obstacles) {
        this.obstacles = obstacles;
    }

    public int getLevel() {
        return level;
    }

    public void changeLevel(int level) throws FileNotFoundException {
        int tempLives = 0;
        if(player != null)
            tempLives = player.lives;
       // System.out.println(tempLives);
        this.level += 1;
          if (this.level < 6) {
        File mapFile = new File("Maps/Map" + this.level + ".txt");
        // File mapFile = new File("Maps/TestMap.txt");

        Scanner sc = new Scanner(mapFile);
        int r = sc.nextInt();
        int c = sc.nextInt();
        board = new WorldObject[r][c];
        String row = sc.nextLine();
        for (int i = 0; i < board.length; i++) {
            row = sc.nextLine();

            for (int j = 0; j < board[0].length; j++) {
                char obj = row.charAt(j);
                if (obj == 'w') {
                    board[i][j] = new Wall(new Rectangle(j * 10, i * 10, 10, 10));
                    //    System.out.println(i*10 + ", " + j*10);
                } else if (obj == 'f') {
                    board[i][j] = new FinishPlatform(new Rectangle(j * 10, i * 10,10,10));
                } else if (obj == '^') {
                    board[i][j] = new LaserEmitter(new Rectangle(j * 10, i * 10,10,10), 100, 75, 0);
                } else if (obj == 'v') {
                    board[i][j] = new LaserEmitter(new Rectangle(j * 10, i * 10,10,10), 100, 75, 1);
                } else if (obj == '<') {
                    board[i][j] = new LaserEmitter(new Rectangle(j * 10, i * 10,10,10), 100, 75, 2);
                } else if (obj == '>') {
                    board[i][j] = new LaserEmitter(new Rectangle(j * 10, i * 10,10,10), 100, 75, 3);
                }
            }
        }
        // player = new Player(new Rectangle(50, 450, 10, 10));

        obstacles = new ArrayList<WorldObject>();
        player = new Player(new Rectangle(sc.nextInt()*10, sc.nextInt()*10, 20, 20));
        if(getLevel()>1){
            getPlayer().setLives(tempLives + 1);

        }
        int numObs = sc.nextInt();
        //System.out.print(numObs);
        // double speed = sc.nextDouble(); //figure this out later :/
        for (int i = 0; i < numObs; i++) {
            sc.nextLine();
            String type = sc.next();
            int x = (int)(sc.nextDouble() * 10);
            int y = (int)(sc.nextDouble() * 10);
            // String type2 = sc.next();

            if (type.compareTo("B") == 0) {
                //  System.out.print("B");
                obstacles.add(i, new BasicObstacle(new Rectangle(x, y, 10, 10),1,(sc.next().compareTo("U") == 0) ? true : false));
                //   System.out.print(getObstacles().get(i).getRect());
                //  obstacles.add(i, new BasicObstacle(new Rectangle((int) (sc.nextDouble() * 10), (int) (sc.nextDouble() * 10)), 1, (sc.next().charAt(0) == 'U') ? true : false));
            } else if (type.compareTo("L") == 0) {
                obstacles.add(i, new LObstacle(new Rectangle((x),  (y), 10, 10), 1, new Point((int) (x), (int) (y)), new Point((int) (sc.nextDouble() * 10), (int) (sc.nextDouble() * 10)), new Point((int) (sc.nextDouble() * 10), (int) (sc.nextDouble() * 10))));
            } else if (type.compareTo("C")== 0) {
                double rad = sc.nextDouble() * 10;
                // System.out.println(" -- " + x + " " + y + " " + rad);
                obstacles.add(i, new CircleObstacle(new Rectangle( (int) (x), (int) (y - rad), 10, 10), 1, new Point((int) (x), (int) (y)), (int) (rad)));
            } else if(type.compareTo("Z") == 0) {
                int length = (int)(sc.nextDouble() * 10);
                int height = 0;
                int width = 0;
                String dir = sc.next();
                int direction = 0;
                       if(dir.equals("U")){
                        height = length;
                        width = 4;
                        y -= length;
                        x+= 3;
                        direction = 0;
                    } else if(dir.equals("D")){
                        height = length;
                        width = 4;
                        y += 10;
                        x+= 3;
                        direction = 1;
                    } else if(dir.equals("L")){
                        height = 3;
                        width = length;
                        x -= length;
                        y += 6;
                        direction = 2;
                    } else if(dir.equals("R")){
                        height = 3;
                        width = length;
                        x += 10;
                        y += 6;
                        direction = 3;
                    }

                obstacles.add(i, new LaserBeam(new Rectangle((int)x, (int)y, width, height), 100, 75, direction));
            }
        }}


    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void checkCollision() throws FileNotFoundException {
        for(int i = 0; i < obstacles.size(); i++){
            if(player.getCollision(obstacles.get(i).getRect())){
                //System.out.println(i);
                reset();
            }
            for(int j = 0; j < obstacles.size(); j++){
                if(j != i){
                    obstacles.get(i).getCollision(obstacles.get(j).getRect());

                }

            }
            if(!(obstacles.get(i) instanceof CircleObstacle)){
                obstacles.get(i).getCollision(new Rectangle(0, 0, board[0].length * 10, 1));
                obstacles.get(i).getCollision(new Rectangle(0, 0, 1,board.length * 10));
                obstacles.get(i).getCollision(new Rectangle(0, board.length * 10, board[0].length * 10, 2));
                obstacles.get(i).getCollision(new Rectangle(board[0].length * 10, 0, 2, board.length * 10));
            }

            for(int r = 0; r < board.length; r++){
                for(int c = 0; c < board[0].length; c++){
                    if(board[r][c] != null){
                        if(obstacles.get(i).getCollision(board[r][c].getRect())){
                        }
                    }
                }
            }
        }
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c] != null && board[r][c].getType() == WorldObject.FINISH){
                    if(player.getCollision(board[r][c].getRect())){
                        changeLevel(level + 1);
                    }
                }
            }
        }


    }

    public void reset() {
        if(player.lives != 0) {
            player.getRect().setLocation(new Point((int)player.x, (int)player.y));
            player.setLives(player.lives - 1);
        }

    }

    public void update() throws FileNotFoundException {
        for(int i = 0; i < obstacles.size(); i++){
            ((Obstacle)(obstacles.get(i))).update();
        }
        checkCollision();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] instanceof LaserEmitter){
                    ((LaserEmitter)board[i][j]).update();
                }
            }
        }

    }

   /* public String toString() {
        String s = "";
        for (int r = 0; r < board.length; r++) {
            s += "\n";
            for (int c = 0; c < board[0].length; c++) {
                s += board[r][c];
            }
        }
        return s;
    }
    */
}
