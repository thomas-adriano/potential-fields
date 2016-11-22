package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class ArtificialPotentialField {

    private static final int SPREAD = 1;
    private final Map map;
    private final Cell objective;
    private final Cell agent;
    private final int objectiveRadius;
    private Map resolved;


    public ArtificialPotentialField(Map map) {
        this.map = map;
        this.objective = findObjectiveCell();
        this.agent = findStartCell();
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

    public final Map resolveMap() {
        if (resolved != null) {
            return resolved;
        }
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


        double finalAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        double velocity = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        System.out.println("Coord. objetivo: " + objective);
        System.out.println("Coord. agent: " + agent);
        System.out.println("Distancia: " + distance);
        System.out.println("Angulo: " + angleObjectiveToAgent);
        System.out.println("Delta x/y: " + deltaX + " / " + deltaY);
        System.out.println("Angulo final: " + finalAngle);
        System.out.println("Velocidade: "+ velocity);

        return resolved;
    }

    private double euclideanDistance(Coordinate start, Coordinate end) {
        double xCoord = Math.pow(end.getX() - start.getX(), 2);
        double yCoord = Math.pow(start.getY() - end.getY(), 2);
        return Math.sqrt(xCoord + yCoord);
    }

}
