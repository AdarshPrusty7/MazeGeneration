import javax.swing.*;

public class MazeApp {
    public static void main (String[] args) {
        //Maze maze = new Maze(3,3);

        MazeDrawing mazeDrawing = new MazeDrawing(10, 10);
        JFrame jf = new JFrame();

        int windowSize = Math.min(mazeDrawing.getWindowHeight(), mazeDrawing.getWindowWidth());
        jf.setTitle("Maze Generation");
        jf.setSize(windowSize+150,windowSize+150); // Refers to size of window
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(mazeDrawing);
    }

}
