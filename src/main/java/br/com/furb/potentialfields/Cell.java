package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class Cell {

    private final Action action;
    private final int radius;
    private final Coordinate coord;
    private final CellTypes cellType;

    public Cell(Coordinate coord, Action action) {
        this(action, CellTypes.NORMAL, 0, coord);
    }

    public Cell(Coordinate coord, CellTypes type) {
        this(Action.emptyAction(), type, 0, coord);
    }

    public Cell(Coordinate coord, int radius, CellTypes type) {
        this(Action.emptyAction(), type, radius, coord);
    }

    public Cell(Cell cell) {
        this(cell.getAction(), cell.getCellType(), cell.getRadius(), cell.getCoord());
    }

    public Cell(Cell cell, Coordinate coord) {
        this(cell.getAction(), cell.getCellType(), cell.getRadius(), coord);
    }

    public Cell(Cell cell, Action act) {
        this(act, cell.getCellType(), cell.getRadius(), cell.getCoord());
    }

    public Cell(Action action, CellTypes type, int radius, Coordinate coord) {
        this.action = action;
        this.cellType = type;
        this.radius = radius;
        this.coord = coord;
    }

    public Action getAction() {
        return action;
    }

    public CellTypes getCellType() {
        return cellType;
    }

    public int getRadius() {
        return radius;
    }

    public Coordinate getCoord() {
        return coord;
    }

    @Override
    public String toString() {
        return "{" +
                "action=" + action +
                ", radius=" + radius +
                ", coord=" + coord +
                ", cellType=" + cellType +
                '}';
    }
}
