import javax.swing.*;
import java.awt.*;

public class MazeApp {
    public static void main (String[] args) {
        //Maze maze = new Maze(3,3);



        MazeDrawing mazeDrawing = new MazeDrawing(16, 16);

        Dimension d = new Dimension(500, 500);

        JFrame jf = new JFrame();
        jf.setLayout(new GridBagLayout());

        jf.setMinimumSize(d);
        JPanel left = new JPanel(), right = new JPanel();
        left.setBackground(Color.red);
        mazeDrawing.setBackground(Color.green);
        right.setBackground(Color.blue);

        GridBagConstraints c = new GridBagConstraints();

        int windowSize = Math.min(mazeDrawing.getWindowHeight(), mazeDrawing.getWindowWidth());
        jf.setTitle("Maze Generation");

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.2;
        c.weighty = 1;
        c.gridx = 0;
        jf.add(left, c);
        c.weightx = 0.6;
        c.gridx = 1;
        jf.add(mazeDrawing, c);
        c.weightx = 0.2;
        c.gridx = 2;
        jf.add(right, c);

        jf.setSize(windowSize+150,windowSize+150); // Refers to size of window
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
