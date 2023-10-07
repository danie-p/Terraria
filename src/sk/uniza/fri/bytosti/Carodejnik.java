package sk.uniza.fri.bytosti;

import sk.uniza.fri.predmety.Nastroj;
import sk.uniza.fri.svet.NeexistujucePolickoException;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda Carodejnik je potomkom Nepriatela
 * Definuje jeho utocnu sylu a unikatny pohyb
 *
 * @author Daniela Pavlikova
 */
public class Carodejnik extends Nepriatel {

    private int pocitadlo;

    public Carodejnik() {
        super(new HpBar(150), 10, 1, "carodejnik", new Nastroj("KAMENNA_LOPATA", 12, 20, 10));
        this.pocitadlo = 0;
    }

    /**
     *
     * @param nullovyPovrch kde sa moze pohybovat
     * @param zem
     */
    @Override
    public void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem) {
        if (this.pocitadlo == 500) {
            try {
                int index = new Random().nextInt(nullovyPovrch.size() - 1);
                Policko nahodnePolickoPovrch = nullovyPovrch.get(index);
                int yNovy = 0;
                if (nahodnePolickoPovrch.getRiadok() != 0) {
                    yNovy = nahodnePolickoPovrch.getRiadok() * Policko.VELKOST_POLICKA;
                }
                Policko nahodnePolicko = zem.dajPolicko(nahodnePolickoPovrch.getRiadok() - 1, nahodnePolickoPovrch.getStlpec());
                zem.odoberNeprazdnePolicko(this.getPolicko());
                this.setPolicko(nahodnePolicko);
                this.posunNa(nahodnePolickoPovrch.getStlpec() * Policko.VELKOST_POLICKA, yNovy);
                zem.pridajNeprazdnePolicko(nahodnePolicko);
                super.getHpBar().posunNa(this.getX(), this.getY() - super.getHpBar().getVyska() - super.getHpBar().getVyska() / 2);
                this.pocitadlo = 0;
            } catch (NeexistujucePolickoException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Chyba pri pozastavení threadu");
                }
                this.pocitadlo = 498;
            }
        }
        this.pocitadlo++;
    }
}
