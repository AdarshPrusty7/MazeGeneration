
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
                    g2d.drawLine(cell.getxPos()*multiplier,(cell.getyPos()+1)*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos()+1)*multiplier);
                }
                if (cell.isSouthWall()){
                    g2d.drawLine(cell.getxPos()*multiplier,(cell.getyPos())*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos())*multiplier);
                }
                if (cell.isEastWall()) {
                    g2d.drawLine((cell.getxPos()+1)*multiplier,(cell.getyPos())*multiplier,(cell.getxPos()+1)*multiplier,(cell.getyPos()+1)*multiplier );
                }
                if (cell.isWestWall()) {
                    g2d.drawLine((cell.getxPos())*multiplier,(cell.getyPos())*multiplier,(cell.getxPos())*multiplier,(cell.getyPos()+1)*multiplier );
                }
            }
        }
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int height = g2d.getClipBounds().height;
                int width = g2d.getClipBounds().width;

                g2d.scale(width, height);
            }
        });


    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public Maze getMaze() {
        return maze;
    }
}