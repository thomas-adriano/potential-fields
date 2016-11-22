package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 * Implementação baseada no paper: http://phoenix.goucher.edu/~jillz/cs325_robotics/goodrich_potential_fields.pdf
 */
public class Main {

    private static final Map WORLD_MAP = Map.newEmptyMap(4, 4).putObjectiveAt(3, 3).putAgentAt(0, 0);

    public static void main(String[] args) {
        System.out.println("-----> Inicializando com mapa:\n" + WORLD_MAP);

        ArtificialPotentialField apf = new ArtificialPotentialField(WORLD_MAP);
        Action act = apf.resolveAction();

        System.out.println("Angulo: " + act.directionAngle);
        System.out.println("Velocidade: " + act.velocity);
    }
}
