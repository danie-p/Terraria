package sk.uniza.fri.predmety;

/**
 * Predok vsetkych inych predmetov, trieda Predmet, definuje zakladne atributy
 *
 * @author danie
 */
public abstract class Predmet implements IPredmet {

    private String nazov;
    private double hmotnost;

    public Predmet(String nazov, double hmotnost) {
        this.nazov = nazov;
        this.hmotnost = hmotnost;
    }

    public String getNazov() {
        return this.nazov;
    }

    public double getHmotnost() {
        return this.hmotnost;
    }
}
