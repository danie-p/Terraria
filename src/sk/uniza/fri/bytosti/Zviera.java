package sk.uniza.fri.bytosti;

import sk.uniza.fri.IDavajuciLoot;
import sk.uniza.fri.predmety.Jedlo;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Zviera je bytost, ktore po umrti za sebou zanecha jedlo
 *
 * @author Daniela Pavlikova
 */
public class Zviera extends PasivnaBytost implements IDavajuciLoot {

    private Jedlo loot;
    private int pocitadlo;

    public Zviera() {
        super(new HpBar(30), 4, 4, "zviera");
        this.loot = new Jedlo("BRAVCOVE_MASO", 2.5, 10);
    }

    /**
     * Prekryta abstraktna metoda predka
     * @param nullovyPovrch
     * @param zem
     */
    @Override
    public void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem) {
        if (this.pocitadlo == 50) {
            this.setvSkoku(true);
            this.posunNa(this.getX(), this.getY() - 10);
            this.let();
            this.pocitadlo = 0;
            this.setvSkoku(false);
        }
        this.pocitadlo++;
    }

    /**
     * Vracia polozku jedla
     * @return
     */
    @Override
    public Predmet getLoot() {
        return this.loot;
    }
}
