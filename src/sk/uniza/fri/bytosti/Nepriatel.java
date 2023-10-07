package sk.uniza.fri.bytosti;

import sk.uniza.fri.IDavajuciLoot;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.predmety.Zbran;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Abstraktna trieda Nepriatel zdruzuje tie bytosti, ktore maju potencial znizit zdravie hraca
 *
 * @author Daniela Pavlikova
 */
public abstract class Nepriatel extends BytostAI implements IAgresor, IPohybujuciSa, IDavajuciLoot {

    private int utocnaSila;
    private Zbran zbran;
    private Predmet loot;

    public Nepriatel(HpBar hpBar, int pocetObrazkov, int pocetObrazkovSkok, String nazov, Zbran zbran) {
        super(hpBar, pocetObrazkov, pocetObrazkovSkok, nazov);
        this.utocnaSila = zbran.getUtocnaSila();
        this.zbran = zbran;
        this.loot = zbran;
    }

    @Override
    public Bytost napadni(Bytost bytost, int silaUtoku) {
        if (bytost instanceof Hrac) {
            if (bytost.upravHp(-silaUtoku)) {
                return bytost;
            }
        }
        return null;
    }

    @Override
    public int getUtocnaSila() {
        return this.utocnaSila;
    }

    @Override
    public abstract void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem);

    @Override
    public Predmet getLoot() {
        return this.loot;
    }
}
