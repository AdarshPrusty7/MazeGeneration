
import javax.swing.*;
import java.awt.*;

public class MazeDrawing extends JPanel {
    private Maze maze;
    private int windowHeight;
    private int windowWidth;

    public MazeDrawing(int width, int height) {
        this.maze = new Maze(width, height);
        this.windowHeight = 500;
        this.windowWidth = 1500;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3f));

        int multiplier = Math.min(windowHeight/maze.getHeight(), windowWidth/ maze.getWidth());
        int adjustor = 50;

        for (int col=0; col<maze.getWidth(); col++) {
            for (int row=0; row<maze.getHeight(); row++) {
                Cell cell = maze.getFinishedMaze()[col][row];


                if (cell.isNorthWall()){
                    g2d.drawLine(cell.getxPos()*multiplier+adjustor,(cell.getyPos()+1)*multiplier+adjustor,(cell.getxPos()+1)*multiplier+adjustor,(cell.getyPos()+1)*multiplier+adjustor);
                }
                if (cell.isSouthWall()){
                    g2d.drawLine(cell.getxPos()*multiplier+adjustor,(cell.getyPos())*multiplier+adjustor,(cell.getxPos()+1)*multiplier+adjustor,(cell.getyPos())*multiplier+adjustor);
                }
                if (cell.isEastWall()) {
                    g2d.drawLine((cell.getxPos()+1)*multiplier+adjustor,(cell.getyPos())*multiplier+adjustor,(cell.getxPos()+1)*multiplier+adjustor,(cell.getyPos()+1)*multiplier+adjustor );
                }
                if (cell.isWestWall()) {
                    g2d.drawLine((cell.getxPos())*multiplier+adjustor,(cell.getyPos())*multiplier+adjustor,(cell.getxPos())*multiplier+adjustor,(cell.getyPos()+1)*multiplier+adjustor );
                }
            }
        }
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }
}