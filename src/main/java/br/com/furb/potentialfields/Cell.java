package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class Cell {

    private final int value;
    private final CellTypes cellType;

    public Cell(int value) {
        this(value, CellTypes.NORMAL);
    }

    public Cell(CellTypes type) {
        this(0, type);
    }

    public Cell(int value, CellTypes type) {
        this.value = value;
        this.cellType = type;
    }

    public int getValue() {
        return value;
    }

    public CellTypes getCellType() {
        return cellType;
    }

    @Override
    public String toString() {
        return "{" + value + "," + cellType + '}';
    }
}
