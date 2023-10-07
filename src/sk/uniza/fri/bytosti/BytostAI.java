package sk.uniza.fri.bytosti;

import sk.uniza.fri.svet.NeexistujucePolickoException;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Trieda BytostAI je potomkom Bytosti, ale urcuje len bytosti neovladane hracom
 * Implementuje interface IPohybujuciSa
 *
 * @author Daniela Pavlikova
 */
public abstract class BytostAI extends Bytost implements IPohybujuciSa {

    private Policko policko;

    public BytostAI(HpBar hpBar, int pocetObrazkov, int pocetObrazkovSkok, String nazov) {
        super(hpBar, pocetObrazkov, pocetObrazkovSkok, nazov);
    }

    @Override
    public abstract void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem);

    public Policko getPolicko() throws NeexistujucePolickoException {
        if (this.policko != null) {
            return this.policko;
        }
        throw new NeexistujucePolickoException();
    }

    public void setPolicko(Policko policko) {
        this.policko = policko;
    }
}
