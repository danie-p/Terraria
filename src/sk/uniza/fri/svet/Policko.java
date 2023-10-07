package sk.uniza.fri.svet;

import sk.uniza.fri.IDavajuciLoot;
import sk.uniza.fri.predmety.Blok;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.tvary.Obrazok;
import sk.uniza.fri.tvary.Platno;

/**
 * Policko definuje zakladne operacie s polickom, ktore sa nacadze v mape (vo svete)
 *
 * @author Daniela Pavlikova
 */
public class Policko implements IDavajuciLoot {

    public static final int VELKOST_POLICKA = Platno.SIRKA_PLATNA / Zem.MAX_SIRKA_ZEME;
    private Obrazok obrazok;
    private int riadok;
    private int stlpec;
    private TypPolicka typ;
    private Blok blok;
    private int odolnost;

    public Policko(int riadok, int stlpec, TypPolicka typ) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.typ = typ;
        if (this.typ != null) {
            this.odolnost = this.typ.getOdolnost();
            this.blok = new Blok(this.typ.toString(), this.typ.getHmotnost());
            this.obrazok = new Obrazok(typ.getCestaKObrazku(), this.stlpec * Policko.VELKOST_POLICKA, this.riadok * Policko.VELKOST_POLICKA);
            this.obrazok.zmenVelkost(Policko.VELKOST_POLICKA, Policko.VELKOST_POLICKA);
            this.obrazok.zobraz();
        }
    }

    public void zobraz() {
        if (this.typ != null) {
            this.obrazok.zobraz();
        } else {
            if (this.obrazok != null) {
                this.obrazok.skry();
                this.obrazok = null;
            }
        }
    }

    public int getRiadok() {
        return this.riadok;
    }

    public int getStlpec() {
        return this.stlpec;
    }

    public TypPolicka getTyp() {
        return this.typ;
    }

    public int getOdolnost() {
        return this.odolnost;
    }

    public void setOdolnost(int odolnost) {
        this.odolnost = odolnost;
    }

    /**
     * Nastavi typ policka, zobrazi mu obrazok, pripadne ho adekvatne premiestni
     * @param typ
     */
    public void setTyp(TypPolicka typ) {
        this.typ = typ;
        if (this.typ != null) {
            this.blok = new Blok(this.typ.toString(), this.typ.getHmotnost());
            if (this.obrazok != null) {
                this.obrazok.zmenObrazok(this.typ.getCestaKObrazku());
                this.obrazok.zmenVelkost(Policko.VELKOST_POLICKA, Policko.VELKOST_POLICKA);
            } else {
                this.obrazok = new Obrazok(this.typ.getCestaKObrazku());
                this.obrazok.zmenPolohu(this.getStlpec() * Policko.VELKOST_POLICKA, this.getRiadok() * Policko.VELKOST_POLICKA);
                this.obrazok.zmenVelkost(Policko.VELKOST_POLICKA, Policko.VELKOST_POLICKA);
            }
            this.obrazok.zobraz();
        } else {
            if (this.obrazok != null) {
                this.blok = null;
                this.obrazok.skry();
                this.obrazok = null;
            }
        }
    }

    @Override
    public Predmet getLoot() {
        return this.blok;
    }
}
