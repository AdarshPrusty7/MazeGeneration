
import java.util.ArrayList;
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


        Cell[][] closedMaze = DFSGen(startingCell, skeletonMaze);
        entranceExitMaker(closedMaze);
        this.finishedMaze = closedMaze;
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
                break;
            case "west":
                cell.setWestWall(false);
                nextCell.setEastWall(false);
                break;
        }
    }

    public void entranceExitMaker(Cell[][] closedMaze){
        ArrayList<Cell> horiCells = new ArrayList<>();
        ArrayList<Cell> vertCells = new ArrayList<>();

        for (int col = 0; col<width; col++){
            horiCells.add(closedMaze[col][0]);
            horiCells.add(closedMaze[col][height-1]);
        }
        for (int row = 1; row<height-1; row++){
            vertCells.add(closedMaze[0][row]);
            vertCells.add(closedMaze[width-1][row]);
        }

        Cell cellStart;
        Cell cellEnd;
        for (int i = 0 ; i<2 ; i++) {
            if (ThreadLocalRandom.current().nextInt(2) == 0) {
                int index1 = ThreadLocalRandom.current().nextInt(horiCells.size());
                if (i==0) {
                    cellStart = horiCells.get(index1);
                    horiCellBreaker(cellStart);
                    horiCells.remove(index1);
                } else {
                    cellEnd = horiCells.get(index1);
                    horiCellBreaker(cellEnd);
                    horiCells.remove(index1);
                }
            } else {
                int index1 = ThreadLocalRandom.current().nextInt(vertCells.size());
                if (i==0) {
                    cellStart = vertCells.get(index1);
                    vertCellBreaker(cellStart);
                    vertCells.remove(index1);

                } else {
                    cellEnd = vertCells.get(index1);
                    vertCellBreaker(cellEnd);
                    vertCells.remove(index1);
                }
            }
        }
    }

    public void horiCellBreaker (Cell cell) {
        if (cell.getyPos() == 0) {
            cell.setSouthWall(false);
        } else {
            cell.setNorthWall(false);
        }
    }

    public void vertCellBreaker (Cell cell) {
        if (cell.getxPos() == 0) {
            cell.setWestWall(false);
        } else {
            cell.setEastWall(false);
        }
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getFinishedMaze() {
        return finishedMaze;
    }
}
