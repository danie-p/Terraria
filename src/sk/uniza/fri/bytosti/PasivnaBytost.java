package sk.uniza.fri.bytosti;

import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Pasivne bytosti su tie, ktore hracovi naspat neublizia
 *
 * @author Daniela Pavlikova
 */
public abstract class PasivnaBytost extends BytostAI implements IPohybujuciSa {

    public PasivnaBytost(HpBar hpBar, int pocetObrazkov, int pocetObrazkovSkok, String nazov) {
        super(hpBar, pocetObrazkov, pocetObrazkovSkok, nazov);
    }

    @Override
    public abstract void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem);
}
