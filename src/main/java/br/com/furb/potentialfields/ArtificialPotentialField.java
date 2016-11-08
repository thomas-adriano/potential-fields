package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class ArtificialPotentialField {

    private final Map map;
    private final Cell objective;
    private final Cell agent;
    private Map resolved;


    public ArtificialPotentialField(Map map) {
        this.map = map;
        this.objective = findObjectiveCell();
        this.agent = findStartCell();
    }

    private Cell findObjectiveCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.OBJECTIVE)
                .orElseThrow(() -> new RuntimeException("Celula objetivo não encontrada"));
    }

    private Cell findStartCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.AGENT)
                .orElseThrow(() -> new RuntimeException("Celula inicial não encontrada"));
    }

    public final Map resolveMap() {
        if (resolved != null) {
            return resolved;
        }
        double distance = euclideanDistance(agent.getCoord(), objective.getCoord());


        System.out.println("Coord. objetivo: " + objective);
        System.out.println("Coord. agent: " + agent);
        System.out.println("Distancia: "+distance);
        return resolved;
    }

    private double euclideanDistance(Coordinate start, Coordinate end) {
        double xCoord = Math.pow(end.getX() - start.getX(), 2);
        double yCoord = Math.pow(start.getY() - end.getY(), 2);
        return Math.sqrt(xCoord + yCoord);
    }

}
