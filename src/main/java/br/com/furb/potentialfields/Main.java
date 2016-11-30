package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 * Implementação baseada no paper: http://phoenix.goucher.edu/~jillz/cs325_robotics/goodrich_potential_fields.pdf
 * angulo
 * -190   0
 * -----
 * 190    0
 */
public class Main {

    private static final Map WORLD_MAP = Map.newEmptyMap(6, 6)
            .putObjectiveAt(5, 5)
            .putObstacleAt(3, 3)
            .putAgentAt(0, 0);

    public static void main(String[] args) {
        System.out.println("-----> Inicializando com mapa:\n" + WORLD_MAP.simplifiedToString());

        ArtificialPotentialField apf = new ArtificialPotentialField(WORLD_MAP);
        Map result = apf.resolveMap();

        System.out.println("\n-----> Resultado:\n" + result.simplifiedToString());
    }
}
