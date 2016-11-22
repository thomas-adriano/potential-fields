package br.com.furb.potentialfields;

import java.util.ArrayList;
import java.util.List;
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

    public static Map newEmptyMap(int width, int height) {
        Cell[][] newCells = new Cell[height][width];
        for (int y = 0; y < newCells.length; y++) {
            for (int x = 0; x < newCells[0].length; x++) {
                newCells[y][x] = new Cell(new Coordinate(x, y), CellTypes.NORMAL);
            }
        }
        return new Map(newCells);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void forEach(Consumer<Cell> c) {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                c.accept(cells[y][x]);
            }
        }
    }

    public List<Cell> filter(Predicate<Cell> p) {
        List<Cell> result = new ArrayList<>();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                if (p.test(cells[y][x])) {
                    result.add(cells[y][x]);
                }
            }
        }
        return result;
    }

    public Optional<Cell> filterFirst(Predicate<Cell> p) {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                if (p.test(cells[y][x])) {
                    return Optional.of(cells[y][x]);
                }
            }
        }
        return Optional.empty();
    }

    public Map putNormalAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[y][x] = new Cell(new Coordinate(x, y), CellTypes.NORMAL);
        return new Map(newCells);
    }

    public Map putAgentAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[y][x] = new Cell(new Coordinate(x, y), CellTypes.AGENT);
        return new Map(newCells);
    }

    public Map putObjectiveAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[y][x] = new Cell(new Coordinate(x, y), 1, CellTypes.OBJECTIVE);
        return new Map(newCells);
    }

    public Map putCell(int x, int y, Cell cell) {
        Cell[][] newCells = copyCells(cells);
        newCells[y][x] = new Cell(cell);
        return new Map(newCells);
    }

    public Map putObstacleAt(int x, int y) {
        Cell[][] newCells = copyCells(cells);
        newCells[y][x] = new Cell(new Coordinate(x, y), 1, CellTypes.OBSTACLE);
        return new Map(newCells);
    }

    private static final Cell[][] copyCells(Cell[][] src) {
        Cell[][] tmp = new Cell[src.length][src[0].length];
        for (int y = 0; y < src.length; y++) {
            for (int x = 0; x < src[0].length; x++) {
                tmp[y][x] = src[y][x];
            }
        }
        return tmp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                String content = String.format("%15s", cells[y][x].toString());
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

    public String simplifiedToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                String content = String.format("%10s %s %s", cells[y][x].getCellType(), cells[y][x].getCoord().toString(), cells[y][x].getAction().toString());
                sb.append(content)
                        .append(" | ");
            }
            sb.append("\n");
        }
        String toString = sb.toString();
        return "{\n" +
                toString +
                '}';
    }
}
