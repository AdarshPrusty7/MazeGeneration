
import javax.swing.*;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Maze extends JComponent {

    public static void main (String[] args){

    }

    Maze (int width, int height) {

        Cell[][] skeletonMaze = new Cell[width][height];

        int randomX = ThreadLocalRandom.current().nextInt(0, width);
        int randomY = ThreadLocalRandom.current().nextInt(0, height);
        Cell startingCell = skeletonMaze[randomX][randomY];

        DFSGen(startingCell);
    }

    Maze DFSGen (Cell startingCell) {
        Stack<Cell> cellStack = new Stack<>();

        //startingCell.visited();

        cellStack.push(startingCell);

        //Cell newCell = startingCell.getUnvistedNeighbour();

        // Break


        while (!cellStack.isEmpty()) {
            Cell cell = cellStack.pop();
            cell.visited();




            /*if (newCell == null) { // If newCell's neighbours have already been visited
                cellStack.pop();
                newCell = cellStack.peek(); // newCell is now the previous cell

                newCell = newCell.getUnvistedNeighbour();
            } else {
                newCell.visited();
                cellStack.push(newCell);*/
            }

        }

    }


}
