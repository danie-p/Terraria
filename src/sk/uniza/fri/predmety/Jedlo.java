package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

/**
 * Trieda Jedlo definuje, kolko zdravia sa hracovi prida.
 *
 * @author Daniela Pavlikova
 */
public class Jedlo extends Predmet {

    private int pridaHp;

    public Jedlo(String nazov, double hmotnost, int pridaHp) {
        super(nazov, hmotnost);
        this.pridaHp = pridaHp;
    }

    public int getPridaHp() {
        return this.pridaHp;
    }

    /**
     * Prekryta metoda
     * Ak hrac drzi v ruke jedlo a pouzije ho, prida sa mu zdravie.
     * @param hrac
     * @param bytost
     * @param policko
     * @param zem
     */
    @Override
    public void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem) {
        hrac.upravHp(this.pridaHp);
        hrac.odoberZInventara(this);
    }
}
