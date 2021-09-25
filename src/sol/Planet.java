package sol;

import java.util.Comparator;
import java.util.Objects;

public class Planet implements Comparable<Planet>{
    private final String name;
    private final int durchmesser;
    private final long abstandZurSonne;
    private final double relativesGewicht;
    private final double umrundungszeit;
    private final int monde;
    private final String bemerkung;

    public static final Comparator<Planet> NAME = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return o1.name.compareTo(o2.name);
        }
    };
    public static final Comparator<Planet> GEWICHT = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return Double.compare(o1.getRelativesGewicht(),o2.getRelativesGewicht());
        }
    };
    public static final Comparator<Planet> MONDE = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return Integer.compare(o1.getMonde(), o2.getMonde());
        }
    };
    public static final Comparator<Planet> UMLAUFZEIT = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return Double.compare(o1.getUmrundungszeit(), o2.getUmrundungszeit());
        }
    };
    public static final Comparator<Planet> ABSTANDZURSONNE = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return Long.compare(o1.getAbstandZurSonne(), o2.getAbstandZurSonne());
        }
    };
    public static final Comparator<Planet> DURCHMESSER = new Comparator<Planet>() {
        @Override
        public int compare(Planet o1, Planet o2) {
            return Long.compare(o1.getDurchmesser(), o2.getDurchmesser());
        }
    };

    public Planet(String name, int durchmesser, long abstandZurSonne, double relativesGewicht, double umrundungszeit, int monde, String bemerkung) {
        this.name = name;
        this.durchmesser = durchmesser;
        this.abstandZurSonne = abstandZurSonne;
        this.relativesGewicht = relativesGewicht;
        this.umrundungszeit = umrundungszeit;
        this.monde = monde;
        this.bemerkung = bemerkung;
    }

    //kommt von Comparable
    @Override
    public int compareTo(Planet o) {
        return Long.compare(abstandZurSonne, o.abstandZurSonne);
    }
    //equals, hascode kommen von Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return abstandZurSonne == planet.abstandZurSonne && name.equals(planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, abstandZurSonne);
    }

    public String getName() {
        return name;
    }

    public int getDurchmesser() {
        return durchmesser;
    }

    public long getAbstandZurSonne() {
        return abstandZurSonne;
    }

    public double getRelativesGewicht() {
        return relativesGewicht;
    }

    public double getUmrundungszeit() {
        return umrundungszeit;
    }

    public int getMonde() {
        return monde;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", durchmesser=" + durchmesser +
                ", abstandZurSonne=" + abstandZurSonne +
                ", relativesGewicht=" + String.format("%.3f",relativesGewicht) +
                ", umrundungszeit=" + String.format("%.3f",umrundungszeit) +
                ", monde=" + monde +
                ", bemerkung='" + bemerkung + '\'' +
                '}';
    }
}
