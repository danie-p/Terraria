package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

/**
 * Interface IPredmet zarucuje, ze vsetky druhy predmetov maju v rozhrani spravu pouzi a polymorfne ju pouzivaju kazda svojim sposobom
 *
 * @author Daniela Pavlikova
 */
public interface IPredmet {
    void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem);
}
