import java.util.ArrayList;
import java.util.Random;

public class Cell {
    private boolean isVisited = false;
    private ArrayList<Cell> neighbours = new ArrayList<>();

    public Cell(int xPos, int yPos, int width, int height, Cell[][] skeletonMaze) {
        if (xPos < width) {
            neighbours.add(skeletonMaze[xPos+1][yPos]);
        }
        if (xPos > 0) {
            neighbours.add(skeletonMaze[xPos-1][yPos]);
        }
        if (yPos < height) {
            neighbours.add(skeletonMaze[xPos][yPos+1]);
        }
        if (yPos > 0) {
            neighbours.add(skeletonMaze[xPos][yPos-1]);
        }
    }

    public Cell getUnvistedNeighbour(){
        for (Cell c : neighbours) {
            if (c.isVisited() == false) {
                return c;
            }
        }
        return null;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void visited() {
        isVisited = true;
    }
}
