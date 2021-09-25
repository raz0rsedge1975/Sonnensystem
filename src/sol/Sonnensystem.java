package sol;

import java.util.*;

public class Sonnensystem implements Iterable<Planet>{

    private  String name;
    private  String bemerkung;
    private  final Planet sonne;

    public Planet getSonne() {
        return sonne;
    }

    private final List<Planet> planetenList = new ArrayList<>();
    private int aktuellerListenWert = 0;

    public Sonnensystem(String filename) {
        planetenList.addAll(PlanetDAO.ladePlaneten(filename));
        this.sonne = planetenList.get(0);
        //planetenList.remove(0); //black hole sun, won't she come?
    }

    //von Iterable
    @Override
    public Iterator<Planet> iterator() {
        return planetenList.iterator();
    }

    public Planet naechster() {
        aktuellerListenWert++;
        System.out.println(aktuellerListenWert);
        if (aktuellerListenWert > planetenList.size()-1)
            aktuellerListenWert = 0;
        Planet out = planetenList.get(aktuellerListenWert);
        System.out.println(aktuellerListenWert);
        return out;
    }

    public Planet vorheriger(){
        aktuellerListenWert--;
        if (aktuellerListenWert < 0)
            aktuellerListenWert = planetenList.size()-1;
        Planet out = planetenList.get(aktuellerListenWert);
        System.out.println(aktuellerListenWert);
        return out;
    }

    public void sort(){
        planetenList.sort(Planet.ABSTANDZURSONNE);
    }

    public void sort(Comparator<Planet> object){
        //Speichert den aktuellen Planet als tmp, sortiert die planeten-Liste nach dem gegebenen Comparator
        //prüft, welchen Platz der tmp Planet in der neuen Liste hast, und setzt den aktuellerListenWert
        //auf die aktuelle Listenposition des ausgewählten Planeten
        Planet tmp;
        try {
            tmp = planetenList.get(aktuellerListenWert);
        } catch (Exception e) {
            e.printStackTrace();
            tmp = planetenList.get(0);
        }
        planetenList.sort(object);
        int i = 0;
        for (Planet planet : planetenList) {
            if (planet.equals(tmp))
                aktuellerListenWert = i;

            i++;
        }
    }


}
