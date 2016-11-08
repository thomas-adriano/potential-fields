package br.com.furb.potentialfields;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by thomas on 08/11/16.
 */
public class Map {

    private final Cell[][] cells;

    public Map(Cell[][] cells) {
        this.cells = copyCells(cells);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void forEach(Consumer<Cell> c) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                c.accept(cells[x][y]);
            }
        }
    }

    public Optional<Cell> filterFirst(Predicate<Cell> p) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                if (p.test(cells[x][y])) {
                    return Optional.of(cells[x][y]);
                }
            }
        }
        return Optional.empty();
    }

    public Map putAgentAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[x][y] = new Cell(new Coordinate(x, y), CellTypes.AGENT);
        return new Map(newCells);
    }

    public Map putObjectiveAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[x][y] = new Cell(new Coordinate(x, y), CellTypes.OBJECTIVE);
        return new Map(newCells);
    }

    public Map putObstacleAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[x][y] = new Cell(new Coordinate(x, y), CellTypes.OBSTACLE);
        return new Map(newCells);
    }

    public static Map newEmptyMap(int width, int height) {
        Cell[][] newCells = new Cell[width][height];
        for (int x = 0; x < newCells.length; x++) {
            for (int y = 0; y < newCells[0].length; y++) {
                newCells[x][y] = new Cell(new Coordinate(x, y), 0);
            }
        }
        return new Map(newCells);
    }

    private static final Cell[][] copyCells(Cell[][] src) {
        Cell[][] tmp = new Cell[src.length][src[0].length];
        for (int x = 0; x < src.length; x++) {
            for (int y = 0; y < src[0].length; y++) {
                tmp[x][y] = src[x][y];
            }
        }
        return tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                String content = String.format("%15s", cells[x][y].toString());
                sb.append(content)
                        .append(" | ");
            }
            sb.append("\n");
        }
        String toString = sb.toString();
        return "Map {\n" +
                toString +
                '}';
    }
}
