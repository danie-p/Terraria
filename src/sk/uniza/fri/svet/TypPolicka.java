package sk.uniza.fri.svet;

/**
 * Enum moznych typov policok a ich vlastnosti
 *
 * @author Daniela Pavlikova
 */
public enum TypPolicka {

    TRAVA("images/predmety/TRAVA.png", 0.5, 0, 0, 10),
    HLINA("images/predmety/HLINA.png", 0.5, 0 , 0, 10),
    SKALA("images/predmety/SKALA.png", 1, 12, 2, 50),
    UHLIE("images/predmety/UHLIE.png", 1.2, 7, 4, 70),
    ZELEZNA_RUDA("images/predmety/ZELEZNA_RUDA.png", 1.5, 6, 6, 100),
    MEDENA_RUDA("images/predmety/MEDENA_RUDA.png", 1.5, 5, 8, 100),
    DIAMANTOVA_RUDA("images/predmety/DIAMANTOVA_RUDA.png", 3, 3, 10, 150);

    private String cestaKObrazku;
    private double hmotnost;
    private int uroven;
    private int prVyskytu;
    private int odolnost;

    TypPolicka(String cestaKObrazku, double hmotnost, int uroven, int prVyskytu, int odolnost) {
        this.cestaKObrazku = cestaKObrazku;
        this.hmotnost = hmotnost;
        this.uroven = uroven;
        this.prVyskytu = prVyskytu;
        this.odolnost = odolnost;
    }

    public String getCestaKObrazku() {
        return this.cestaKObrazku;
    }

    public double getHmotnost() {
        return this.hmotnost;
    }

    public int getUroven() {
        return this.uroven;
    }

    public int getPrVyskytu() {
        return this.prVyskytu;
    }

    public int getOdolnost() {
        return odolnost;
    }
}
