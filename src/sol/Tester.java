package sol;

import java.io.IOException;
import java.util.Set;

public class Tester {
    public static void main(String[] args) throws IOException {
        Sonnensystem sol = new Sonnensystem("res/sol.csv");
        Set<Planet> planetSet = PlanetDAO.ladePlaneten("res/sol.csv");
        //Initial Output
        System.out.println("Naturliche Ordnung:");
        listAusgabe(sol);

        System.out.println("Name:");
        sol.sort(Planet.NAME);
        listAusgabe(sol);

        System.out.println("Kleinster Durchmesser:");
        sol.sort(Planet.DURCHMESSER);
        listAusgabe(sol);

        System.out.println("Groesster Abstand zur Sonne:");
        sol.sort(Planet.ABSTANDZURSONNE);
        listAusgabe(sol);

        //Ausgabe Mit Sonne von Mitte
        //for (Planet planet : planetSet) {
        //    System.out.printf(String.valueOf(planet)+'\n');
        //}

        //Teste BildLade
        DateiVerbindung.ladeBild("Erde");


    }
    static void listAusgabe(Sonnensystem sol){
        for (Planet planet : sol) {
            System.out.println(planet);
        }
        System.out.println();
    }
}
