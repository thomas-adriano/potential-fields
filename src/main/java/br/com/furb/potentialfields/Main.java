package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 * Implementação baseada no paper: http://phoenix.goucher.edu/~jillz/cs325_robotics/goodrich_potential_fields.pdf
 * <p>
 * TODO: Atualmente existe alguma discrepancia nas coordenadas.
 * Map.put*At() nao se comporta como esperado em relacao a colocacao nas coordenadas x,y;
 * O Angulo final calculado no APF.resolveMap() esta tendo 0graus como 270graus, 90 como 0 e 45 como 315
 */
public class Main {

    private static final Map WORLD_MAP = Map.newEmptyMap(4, 4).putObjectiveAt(3, 3).putAgentAt(2, 3);

    public static void main(String[] args) {
        System.out.println("-----> Inicializando com mapa:\n" + WORLD_MAP);
        ArtificialPotentialField apf = new ArtificialPotentialField(WORLD_MAP);
        apf.resolveMap();
    }
}
