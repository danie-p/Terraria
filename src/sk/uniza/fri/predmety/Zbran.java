package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

/**
 * Trieda zbran zvysuje utocnu silu hraca, ale aj nepriatela, ak ju vlastni
 *
 * @author Daniela Pavlikova
 */
public class Zbran extends Predmet {

    private int utocnaSila;

    public Zbran(String nazov, double hmotnost, int utocnaSila) {
        super(nazov, hmotnost);
        this.utocnaSila = utocnaSila;
    }

    public int getUtocnaSila() {
        return this.utocnaSila;
    }

    /**
     * Prekryta metoda.
     * Ak hrac drzi v ruke zbran a pouzije ho na bytost, znizi tym zdravie bytosti
     * @param hrac
     * @param bytost
     * @param policko
     * @param zem
     */
    @Override
    public void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem) {
        if (bytost != null) {
            hrac.napadni(bytost, this.utocnaSila);
        }
    }
}
