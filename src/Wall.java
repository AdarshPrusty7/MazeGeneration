public class Wall {
    private Cell[] cells = new Cell[2];


    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    public void setCell(Cell cell, int pos) {
        this.cells[pos] = cell;
    }
}
