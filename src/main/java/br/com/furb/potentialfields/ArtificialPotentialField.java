package br.com.furb.potentialfields;

import java.util.ArrayList;
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
        this.obstacles = findObstacles();
        this.agent = findAgentCell();
        this.objectiveRadius = objective.getRadius();
    }

    private Cell findObjectiveCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.OBJECTIVE)
                .orElseThrow(() -> new RuntimeException("Celula objetivo não encontrada"));
    }

    private Cell findAgentCell() {
        return map.filterFirst((cell) -> cell.getCellType() == CellTypes.AGENT)
                .orElseThrow(() -> new RuntimeException("Celula inicial não encontrada"));
    }

    private List<Cell> findObstacles() {
        return map.filter((cell -> cell.getCellType() == CellTypes.OBSTACLE));
    }

    public Map resolveMap() {
        Map result = new Map(this.map.getCells());
        for (int y = 0; y < map.getCells().length; y++) {
            for (int x = 0; x < map.getCells()[y].length; x++) {
                Action curAct = resolveAction(new Cell(agent, new Coordinate(x, y)));
                result = result.putCell(x, y, new Cell(map.getCell(x, y), curAct));
            }
        }
        return result;
    }

    private final Action resolveAction(Cell agent) {
        Deltas attractionDeltas = resolveAttraction(agent);
        Deltas rejectionDeltas = resolveRejection(agent);
        Deltas finalDeltas = attractionDeltas.combine(rejectionDeltas);

        double finalAngle = Math.toDegrees(Math.atan2(finalDeltas.deltaY, finalDeltas.deltaX));
        double velocity = Math.sqrt(Math.pow(finalDeltas.deltaX, 2) + Math.pow(finalDeltas.deltaY, 2));

        return new Action(finalAngle, velocity);
    }

    private final Deltas resolveAttraction(Cell agent) {
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

    private final Deltas resolveRejection(Cell agent) {
        List<Deltas> obstaclesDeltas = new ArrayList<>();
        obstacles.forEach(o -> {
            double distance = euclideanDistance(agent.getCoord(), o.getCoord());
            double angleObstacleToAgent = Math.atan2(o.getCoord().getY() - agent.getCoord().getY(),
                    o.getCoord().getX() - agent.getCoord().getX());

            double deltaX = 0;
            double deltaY = 0;

//        if d < r
            if (distance < o.getRadius()) {
                deltaX = Math.toDegrees(-1 * (Math.signum(Math.cos(angleObstacleToAgent))));
                deltaY = Math.toDegrees(-1 * (Math.signum(Math.sin(angleObstacleToAgent))));
            }

//        if r≤d≤s+r
            if (o.getRadius() <= distance && distance <= (SPREAD + o.getRadius())) {
                deltaX = Math.toDegrees(-1 * (SPREAD + o.getRadius() - distance) * Math.cos(angleObstacleToAgent));
                deltaY = Math.toDegrees(-1 * (SPREAD + o.getRadius() - distance) * Math.sin(angleObstacleToAgent));
            }

//        if d > s + r
            if (distance > (SPREAD + o.getRadius())) {
                deltaX = 0;
                deltaY = 0;
            }

            obstaclesDeltas.add(new Deltas(deltaX, deltaY));
        });

        return obstaclesDeltas.stream().reduce((prev, actual) -> prev.combine(actual)).orElse(new Deltas(0, 0));
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
