package sk.uniza.fri.bytosti;

import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Vila je pasivna bytost, ktora hracovi dokaze pomoct
 * Ma jedinecne prekrytu metodu pohybuj
 *
 * @author Daniela Pavlikova
 */
public class Vila extends PasivnaBytost {

    private int pocitadlo;
    private static final int MIN_LET = 100;
    private static final int MAX_LET = MIN_LET + 50;

    public Vila() {
        super(new HpBar(50), 27, 27, "vila");
        this.pocitadlo = 0;
    }

    /**
     * Pohybuje sa lietanim
     * @param nullovyPovrch
     * @param zem
     */
    @Override
    public void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem) {
        if (this.pocitadlo >= Vila.MIN_LET && this.pocitadlo < Vila.MAX_LET && this.getY() > 0) {
            this.setvSkoku(true);
            this.posunNa(this.getX(), this.getY() - 1);
            this.let();
        } else if (this.pocitadlo == Vila.MAX_LET) {
            this.pocitadlo = 0;
            this.setvSkoku(false);
        }
        this.pocitadlo++;
    }
}
