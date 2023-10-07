package sk.uniza.fri.bytosti;

import sk.uniza.fri.tvary.Obdlznik;

/**
 * Trieda HpBar urcuje a graficky znazornuje zdravie bytosti
 *
 * @author Daniela Pavlikova
 */
public class HpBar {

    private static final int POSUN = 3;
    private static final int POVODNA_SIRKA_VONK = 100;
    private static final int POVODNA_VYSKA_VONK = 20;
    private static final int POVODNA_SIRKA_VNUT = 94;
    private static final int POVODNA_VYSKA_VNUT = 14;
    private int hp;
    private int hpPovodne;
    private Obdlznik vonkajsi;
    private Obdlznik vnutorny;

    public HpBar(int hp) {
        this.hp = hp;
        this.hpPovodne = hp;
        this.vonkajsi = new Obdlznik(0, 0, HpBar.POVODNA_SIRKA_VONK, HpBar.POVODNA_VYSKA_VONK, "black");
        this.vnutorny = new Obdlznik(HpBar.POSUN, HpBar.POSUN, HpBar.POVODNA_SIRKA_VNUT, HpBar.POVODNA_VYSKA_VNUT, "green");
    }

    public Obdlznik getVonkajsi() {
        return this.vonkajsi;
    }

    /**
     *
     * @param kolko o kolko sa ma upravit zdravie bytosti, zaporne ubera zdravie, kladne pridava zdravie
     */
    public void upravHp(int kolko) {
        if (this.hp + kolko <= this.hpPovodne) {
            this.hp += kolko;
            this.vnutorny.zmenStrany(this.vnutorny.getStranaA() + ((HpBar.POVODNA_SIRKA_VNUT * kolko) / this.hpPovodne), HpBar.POVODNA_VYSKA_VNUT);
        } else {
            this.hp = this.hpPovodne;
            this.vnutorny.zmenStrany(HpBar.POVODNA_SIRKA_VNUT, HpBar.POVODNA_VYSKA_VNUT);
        }
        this.zobraz();
    }

    /**
     * Posun grafickeho znazornenia zdravia
     * @param x
     * @param y
     */
    public void posunNa(int x, int y) {
        this.vonkajsi.posunNaXY(x, y);
        this.vnutorny.posunNaXY(x + 3, y + 3);
        this.zobraz();
    }

    public int getHp() {
        return this.hp;
    }

    public int getVyska() {
        return this.vonkajsi.getStranaB();
    }

    public void zobraz() {
        this.vonkajsi.zobraz();
        this.vnutorny.zobraz();
    }

    public void skry() {
        this.vonkajsi.skry();
        this.vnutorny.skry();
    }
}

