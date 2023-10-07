package sk.uniza.fri.predmety;

import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.TypPolicka;
import sk.uniza.fri.svet.Zem;

/**
 * Blok je taky predmet, ktory sa da polozit do mapy policok
 *
 * @author Daniela Pavlikova
 */
public class Blok extends Predmet {

    private TypPolicka typPolicka;

    public Blok(String nazov, double hmotnost) {
        super(nazov, hmotnost);
        this.typPolicka = TypPolicka.valueOf(nazov);
    }

    /**
     * Prekryta metoda interface-u IPredmet
     * Definuje, co sa stane, ak hrac drzi v ruke blok
     * @param hrac
     * @param bytost
     * @param policko
     * @param zem
     */
    @Override
    public void pouzi(Hrac hrac, BytostAI bytost, Policko policko, Zem zem) {
        if (bytost == null && policko.getTyp() == null) {
            zem.nastavTypPolicka(policko, this.typPolicka);
            zem.pridajNeprazdnePolicko(policko);
            zem.zobraz();
            hrac.odoberZInventara(this);
        }
    }
}
