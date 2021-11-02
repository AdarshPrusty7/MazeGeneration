import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {
    private int xPos;
    private int yPos;

    private boolean isVisited = false;
    private ArrayList<Cell> neighbours = new ArrayList<>();

    private boolean northWall = true;
    private boolean southWall = true;
    private boolean eastWall = true;
    private boolean westWall = true;




    public Cell(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Cell getUnvisitedNeighbour(){
        ArrayList<Cell> unvisitedNeighbours = neighbours;

        while (!unvisitedNeighbours.isEmpty()) {
            int index = ThreadLocalRandom.current().nextInt(unvisitedNeighbours.size());
            Cell cell = unvisitedNeighbours.get(index);
            if (cell.isVisited) {
                unvisitedNeighbours.remove(index);
            } else {
                return cell;
            }
        }
        return null;
    }

    public void initNeighbours(int width, int height, Cell initCell, Cell[][] skeletonMaze){
        if (this.xPos < width) {
            if (initCell.getxPos() == this.xPos+1 && initCell.getyPos() == this.yPos) {
                neighbours.add(initCell);
            } else {
                if (skeletonMaze[xPos + 1][yPos] == null) {
                    Cell neighbourCell = new Cell(this.xPos + 1, this.yPos);
                    skeletonMaze[xPos + 1][yPos] = neighbourCell;
                    neighbours.add(neighbourCell);
                } else {
                    neighbours.add(skeletonMaze[xPos+1][yPos]);
                }
            }
        }
        if (this.xPos > 0) {
            if (initCell.getxPos() == this.xPos - 1 && initCell.getyPos() == this.yPos) {
                neighbours.add(initCell);
            } else {
                if (skeletonMaze[xPos - 1][yPos] == null) {
                    Cell neighbourCell = new Cell(this.xPos - 1, this.yPos);
                    skeletonMaze[xPos - 1][yPos] = neighbourCell;
                    neighbours.add(neighbourCell);
                } else {
                    neighbours.add(skeletonMaze[xPos-1][yPos]);
                }
            }
        }
        if (this.yPos < height) {
            if (initCell.getxPos() == this.xPos && initCell.getyPos() == this.yPos + 1) {
                neighbours.add(initCell);
            } else {
                if (skeletonMaze[xPos][yPos + 1] == null) {
                    Cell neighbourCell = new Cell(this.xPos, this.yPos + 1);
                    skeletonMaze[xPos][yPos + 1] = neighbourCell;
                    neighbours.add(neighbourCell);
                } else {
                    neighbours.add(skeletonMaze[xPos][yPos + 1]);
                }
            }
        }
        if (this.yPos > 0) {
            if (initCell.getxPos() == this.xPos && initCell.getyPos() == this.yPos - 1) {
                neighbours.add(initCell);
            } else {
                if (skeletonMaze[xPos][yPos - 1] == null) {
                    Cell neighbourCell = new Cell(this.xPos, this.yPos - 1);
                    skeletonMaze[xPos][yPos - 1] = neighbourCell;
                    neighbours.add(neighbourCell);
                } else {
                    neighbours.add(skeletonMaze[xPos][yPos - 1]);
                }
            }
        }
    }




    public boolean isVisited() {
        return isVisited;
    }

    public void visited() {
        isVisited = true;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setxyPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean isNorthWall() {
        return northWall;
    }

    public void setNorthWall(boolean northWall) {
        this.northWall = northWall;
    }

    public boolean isSouthWall() {
        return southWall;
    }

    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    public boolean isEastWall() {
        return eastWall;
    }

    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    public boolean isWestWall() {
        return westWall;
    }

    public void setWestWall(boolean westWall) {
        this.westWall = westWall;
    }
}
