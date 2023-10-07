package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

/**
 * Trieda Material zatial nebola v hre pouzita, potencial do buducnosti.
 *
 * @author Daniela Pavlikova
 */
public class Material extends Predmet {

    public Material(String nazov, double hmotnost) {
        super(nazov, hmotnost);
    }

    @Override
    public void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem) {

    }
}
