package br.com.furb.potentialfields;

/**
 * Created by thomas on 08/11/16.
 */
public class Main {

    private static final Map WORLD_MAP = Map.newEmptyMap(4, 4).putObjectiveAt(3, 3).putStartAt(0, 0);

    public static void main(String[] args) {
        System.out.println("-----> Inicializando com mapa:\n" + WORLD_MAP);

    }
}
