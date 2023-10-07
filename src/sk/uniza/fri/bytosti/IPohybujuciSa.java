package sk.uniza.fri.bytosti;

import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;

import java.util.ArrayList;

/**
 * Interface pre non-player characters, teda bytosti neovladane hracom
 *
 * @author Daniela Pavlikova
 */
public interface IPohybujuciSa {
    void pohybuj(ArrayList<Policko> nullovyPovrch, Zem zem);
}
