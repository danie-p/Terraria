package sk.uniza.fri.bytosti;

import sk.uniza.fri.predmety.Zbran;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 *
 * Trieda Bojovnik uchovava informacie o potomkovi triedy Nepriatel.
 * Definuje utocnu silu tohto druhu nepriatela.
 * Obsahuje prekrytie abstraktnej metody pohybuj.
 *
 * @author Daniela Pavlikova
 */
public class Bojovnik extends Nepriatel {

    private int pocitadlo;

    public Bojovnik() {
        super(new HpBar(200), 5, 5, "bojovnik", new Zbran("TEMNY_MEC", 5.65, 15));
        this.pocitadlo = 0;
    }

    /**
     * Jedinecne prekrytie abstraktnej metody pohybuj
     * @param nullovyPovrch umoznuje pohyb
     * @param zem
     */
    @Override
    public void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem) {
        if (this.pocitadlo == 100) {
            if (this.getSmer() == Smer.DOPRAVA) {
                this.setSmer(Smer.DOLAVA);
            } else {
                this.setSmer(Smer.DOPRAVA);
            }
        } else if (this.pocitadlo > 100 && this.pocitadlo < 100 + (Policko.VELKOST_POLICKA - this.getSirka())) {
            this.posunNa(this.getX() + this.getSmer().getZmena(), this.getY());
            super.getHpBar().posunNa(this.getX(), this.getY() - super.getHpBar().getVyska() - super.getHpBar().getVyska() / 2);
        } else if (this.pocitadlo == 100 + (Policko.VELKOST_POLICKA - this.getSirka())) {
            this.pocitadlo = 0;
        }
        this.pocitadlo++;
    }
}
