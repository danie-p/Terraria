package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

/**
 * Nastroj je takym predmetom, ktory sa moze opotrebovat, ale sluzi aj ako zbran.
 *
 * @author Daniela Pavlikova
 */
public class Nastroj extends Zbran {

    private int zivotnost;

    public Nastroj(String nazov, double hmotnost, int zivotnost, int utocnaSila) {
        super(nazov, hmotnost, utocnaSila);
        this.zivotnost = zivotnost;
    }

    public int getZivotnost() {
        return this.zivotnost;
    }

    /**
     * Prekryva metodu interface-u.
     * Ak hrac drzi v ruke nastroj a pouziva ho, jeho zivitnost sa znizi.
     * Zrychluje nicenie policok, ale aj bytosti.
     * @param hrac
     * @param bytost
     * @param policko
     * @param zem
     */
    @Override
    public void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem) {
        if (policko.getTyp() != null) {
            hrac.znic(policko, zem, super.getUtocnaSila());
            this.zivotnost--;
            if (this.zivotnost == 0) {
                hrac.odoberZInventara(this);
            }
        }
        if (bytost != null) {
            hrac.napadni(bytost, super.getUtocnaSila());
        }
    }
}
