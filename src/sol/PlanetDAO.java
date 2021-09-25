package sol;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PlanetDAO {

    private PlanetDAO(){}

//statische Methode
    public static Set<Planet> ladePlaneten(String dateiname){
        List<String> zuVerarbeitenListe = DateiVerbindung.liesZeilenweise(dateiname);
        zuVerarbeitenListe.remove(0); //Entferne Kopfzeile
        Set<Planet> planeten = new TreeSet<>(); //Ausgabedatenstruktur, als Treeset weil besser zu sortieren


        //Achtung, die PLanetenliste wird mit der Sonne als 0ter Planet geladen, dabei ist sie ein STERN!
        for (String s : zuVerarbeitenListe) {
            String[] daten = s.split(";");

            try {
                planeten.add(
                        new Planet(
                                daten[0],
                                Integer.parseInt(daten[1]),
                                Long.parseLong(daten[2]),
                                Double.parseDouble(daten[3]),
                                Double.parseDouble(daten[4]),
                                Integer.parseInt(daten[5]),
                                daten[6]
                        ));

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return planeten;
    }
}
