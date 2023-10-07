package sk.uniza.fri.predmety;

/**
 * Trieda Polozka zdruzuje druh predmetu a pocet kusov.
 * Pouziva sa ako polozka inventara.
 *
 * @author Daniela Pavlikova
 */
public class Polozka {

    private Predmet predmet;
    private int pocetKs;

    public Polozka(Predmet predmet) {
        this.predmet = predmet;
        this.pocetKs = 1;
    }

    public Predmet getPredmet() {
        return this.predmet;
    }

    public void pridajKus() {
        this.pocetKs++;
    }

    public void odoberKus() {
        if (this.pocetKs > 0) {
            this.pocetKs--;
        }
    }

    public int getPocetKs() {
        return this.pocetKs;
    }
}
