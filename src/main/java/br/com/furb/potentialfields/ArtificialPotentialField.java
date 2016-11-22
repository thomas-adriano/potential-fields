package br.com.furb.potentialfields;

import java.util.List;

/**
 * Created by thomas on 08/11/16.
 */
public class ArtificialPotentialField {

    private static final int SPREAD = 1;
    private final Map map;
    private final Cell objective;
    private final Cell agent;
    private final List<Cell> obstacles;
    private final int objectiveRadius;

    public ArtificialPotentialField(Map map) {
        this.map = map;
        this.objective = findObjectiveCell();
        this.agent = findStartCell();
        this.obstacles = findObstacles();
        this.objectiveRadius = objective.getRadius();
    }

    private Cell findObjectiveCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.OBJECTIVE)
                .orElseThrow(() -> new RuntimeException("Celula objetivo não encontrada"));
    }

    private Cell findStartCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.AGENT)
                .orElseThrow(() -> new RuntimeException("Celula inicial não encontrada"));
    }

    private List<Cell> findObstacles() {
        return map.filter((cell -> cell.getCellType() == CellTypes.OBSTACLE));
    }

    public final Action resolveAction() {
        Deltas attractionDeltas = resolveAttraction();
        Deltas rejectionDeltas = resolveRejection();
        Deltas finalDeltas = attractionDeltas.combine(rejectionDeltas);

        double finalAngle = Math.toDegrees(Math.atan2(finalDeltas.deltaY, finalDeltas.deltaX));
        double velocity = Math.sqrt(Math.pow(finalDeltas.deltaX, 2) + Math.pow(finalDeltas.deltaY, 2));

        return new Action(finalAngle, velocity);
    }

    private final Deltas resolveAttraction() {
        double distance = euclideanDistance(agent.getCoord(), objective.getCoord());
        double angleObjectiveToAgent = Math.atan2(objective.getCoord().getY() - agent.getCoord().getY(),
                objective.getCoord().getX() - agent.getCoord().getX());

        double deltaX = 0;
        double deltaY = 0;
//        if d < r
        if (distance < objectiveRadius) {
            deltaX = 0;
            deltaY = 0;
        }

//        if r≤d≤s+r
        if (objectiveRadius <= distance && distance <= (SPREAD + objectiveRadius)) {
            deltaX = Math.toDegrees(distance - objectiveRadius) * Math.cos(angleObjectiveToAgent);
            deltaY = Math.toDegrees(distance - objectiveRadius) * Math.sin(angleObjectiveToAgent);
        }

//        if d > s + r
        if (distance > (SPREAD + objectiveRadius)) {
            deltaX = Math.toDegrees(SPREAD) * Math.cos(angleObjectiveToAgent);
            deltaY = Math.toDegrees(SPREAD) * Math.sin(angleObjectiveToAgent);
        }

        return new Deltas(deltaX, deltaY);
    }

    private final Deltas resolveRejection() {
        return new Deltas(0, 0);
    }

    private double euclideanDistance(Coordinate start, Coordinate end) {
        double xCoord = Math.pow(end.getX() - start.getX(), 2);
        double yCoord = Math.pow(start.getY() - end.getY(), 2);
        return Math.sqrt(xCoord + yCoord);
    }

    private class Deltas {
        public final double deltaX;
        public final double deltaY;

        public Deltas(double deltaX, double deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public Deltas combine(Deltas d) {
            double dX = this.deltaX + d.deltaX;
            double dY = this.deltaY + d.deltaY;
            return new Deltas(dX, dY);
        }
    }

}
