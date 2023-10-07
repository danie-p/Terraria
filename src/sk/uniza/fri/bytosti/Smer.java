package sk.uniza.fri.bytosti;

/**
 * Enum moznych smerov pohybu/akcie
 *
 * @author Daniela Pavlikova
 */
public enum Smer {

    DOPRAVA(1),
    DOLAVA(-1),
    HORE(-1),
    DOLE(1);

    private int zmena;

    Smer(int zmena) {
        this.zmena = zmena;
    }

    public int getZmena() {
        return this.zmena;
    }
}
