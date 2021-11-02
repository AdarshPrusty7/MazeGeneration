
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Maze {
    private int width;
    private int height;
    private final Cell[][] finishedMaze;

    Maze (int width, int height) {
        this.width = width;
        this.height = height;
        Cell[][] skeletonMaze = new Cell[width][height];

        int randomX = ThreadLocalRandom.current().nextInt(width);
        int randomY = ThreadLocalRandom.current().nextInt(height);

        Cell startingCell = new Cell(randomX, randomY);
        skeletonMaze[randomX][randomY] = startingCell;


        this.finishedMaze = DFSGen(startingCell, skeletonMaze);
        System.out.println("Maze finished");
    }

    Cell[][] DFSGen (Cell startingCell, Cell[][] skeletonMaze) {
        Stack<Cell> cellStack = new Stack<>();

        cellStack.push(startingCell);
        Cell cell = startingCell;
        Cell badCell = new Cell(-1, -1);
        cell.initNeighbours(width-1, height-1, badCell, skeletonMaze);

        while (!cellStack.isEmpty()) {
            cell.visited();
            Cell nextCell = cell.getUnvisitedNeighbour();
            if (nextCell != null) {
                nextCell.initNeighbours(width-1, height-1, cell, skeletonMaze);
                WallBreaker(cell, nextCell);
                cellStack.add(cell);
                cell = nextCell;
            } else {
                cell = cellStack.pop();
            }
        }

        return skeletonMaze;
    }

    String FindCellRelation(Cell startCell, Cell endCell) {
        if (startCell.getxPos() == endCell.getxPos()) {
            if (startCell.getyPos() < endCell.getyPos()) {
                return "north";
            } else {
                return "south";
            }
        } else if (startCell.getxPos() < endCell.getxPos()) {
            return "east";
        } else {
            return "west";
        }
    }

    void WallBreaker(Cell cell, Cell nextCell) {
        switch (FindCellRelation(cell, nextCell)) {
            case "north":
                cell.setNorthWall(false);
                nextCell.setSouthWall(false);
                break;
            case "south":
                cell.setSouthWall(false);
                nextCell.setNorthWall(false);
                break;
            case "east":
                cell.setEastWall(false);
                nextCell.setWestWall(false);
            case "west":
                cell.setWestWall(false);
                nextCell.setEastWall(false);
        }
    }
}
