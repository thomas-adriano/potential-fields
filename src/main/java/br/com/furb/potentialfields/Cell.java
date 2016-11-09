package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class Cell {

    private final int value;
    private final int radius;
    private final Coordinate coord;
    private final CellTypes cellType;

    public Cell(Coordinate coord, int value) {
        this(value, CellTypes.NORMAL, 0, coord);
    }

    public Cell(Coordinate coord, CellTypes type) {
        this(0, type, 0, coord);
    }

    public Cell(int value, CellTypes type, int radius, Coordinate coord) {
        this.value = value;
        this.cellType = type;
        this.radius = radius;
        this.coord = coord;
    }

    public int getValue() {
        return value;
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
                "value=" + value +
                ", radius=" + radius +
                ", coord=" + coord +
                ", cellType=" + cellType +
                '}';
    }
}
