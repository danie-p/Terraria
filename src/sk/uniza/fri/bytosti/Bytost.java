package sk.uniza.fri.bytosti;

import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.tvary.Obrazok;
import sk.uniza.fri.tvary.Platno;

import java.util.ArrayList;

/**
 * Abstraktna trieda bytost je predlohou pre vsetky bytosti, vratane hraca.
 * Obsahuje vsetky esencialne atributy a zakladne funkcie vykonavane bytostami
 *
 * @author Daniela Pavlikova
 */
public abstract class Bytost {

    private static final int TOLERANCIA = 5;
    public static final int VYSKA_SKOKU = Policko.VELKOST_POLICKA / 3;
    public static final int VZDIALENOST_POHYBU = 5;
    private HpBar hpBar;
    private int animacia;
    private int animaciaSkok;
    private int pocetObrazkov;
    private int pocetObrazkovSkok;
    private String nazov;
    private int sirka;
    private int vyska;
    private Obrazok obrazok;
    private Smer smer;
    private boolean vSkoku;
    private boolean vPade;
    private int skokPocitadlo;

    public Bytost(HpBar hpBar, int pocetObrazkov, int pocetObrazkovSkok, String nazov) {
        this.animacia = 1;
        this.animaciaSkok = 1;
        this.pocetObrazkov = pocetObrazkov;
        this.pocetObrazkovSkok = pocetObrazkovSkok;
        this.nazov = nazov;
        this.vSkoku = false;
        this.vPade = false;
        this.smer = Smer.DOLAVA;
        this.skokPocitadlo = 0;
        this.obrazok = new Obrazok("images/" + this.nazov + "/krok/" + this.smer + "/" + this.animacia + ".png");
        this.vyska = Policko.VELKOST_POLICKA;
        this.sirka = (this.vyska * this.obrazok.sirka()) / this.obrazok.vyska();
        this.obrazok.zmenVelkost(this.sirka, this.vyska);
        this.hpBar = hpBar;
        this.hpBar.posunNa(this.getX(), this.getY() - this.hpBar.getVyska() * 2);
    }

    public void posunNa(int x, int y) {
        this.obrazok.zmenPolohu(x, y);
    }

    public void initObrazok() {
        this.obrazok.zmenObrazok("images/" + this.nazov + "/krok/" + this.smer + "/1.png");
    }

    protected Obrazok getObrazok() {
        return this.obrazok;
    }

    public int getX() {
        return this.obrazok.getPolohaX();
    }

    public int getY() {
        return this.obrazok.getPolohaY();
    }

    public int getSirka() {
        return this.obrazok.sirka();
    }

    protected int getVyska() {
        return this.obrazok.vyska();
    }

    protected String getNazov() {
        return this.nazov;
    }

    public Smer getSmer() {
        return this.smer;
    }

    protected int getSkokPocitadlo() {
        return this.skokPocitadlo;
    }

    protected HpBar getHpBar() {
        return this.hpBar;
    }

    /**
     * Urcuje stav bytosti, ci stoji alebo je v lete/v pade
     * @param nenullovePolicka urcuju, ci bytost niekde stoji
     * @return
     */
    public Policko stojiNaPolicku(ArrayList<Policko> nenullovePolicka) {
        for (Policko policko : nenullovePolicka) {
            if (this.getX() + this.sirka - Bytost.TOLERANCIA > policko.getStlpec() * Policko.VELKOST_POLICKA &&
                    this.getX() + Bytost.TOLERANCIA < policko.getStlpec() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA &&
                    this.getY() + this.vyska >= policko.getRiadok() * Policko.VELKOST_POLICKA &&
                    this.getY() + this.vyska <= policko.getRiadok() * Policko.VELKOST_POLICKA + Bytost.VZDIALENOST_POHYBU) {
                this.obrazok.zmenPolohu(this.getX(), policko.getRiadok() * Policko.VELKOST_POLICKA - this.vyska);
                return policko;
            }
        }
        return null;
    }

    /**
     * Definuje padanie bytosti a zmeny atributov pri nom
     */
    public void pad() {
        if (this.getY() + this.vyska <= Platno.VYSKA_PLATNA) {
            this.vPade = true;
            this.obrazok.zmenPolohu(this.getX(), this.getY() + 5);
            this.let();
        }
        this.vPade = false;
    }

    /**
     * Definuje animacie pocas letu bytosti
     */
    public void let() {
        this.hpBar.posunNa(this.getX(), this.getY() - this.hpBar.getVyska() - this.hpBar.getVyska() / 2);
        this.obrazok.zmenObrazok("images/" + this.nazov + "/skok/" + this.smer + "/" + this.animaciaSkok + ".png");
        if (this.animaciaSkok < this.pocetObrazkovSkok) {
            this.animaciaSkok++;
        } else {
            this.animaciaSkok = 1;
        }
    }

    /***
     * Urcuje, ako bude vyzerat jeden krok animacie bytosti
     */
    public void krok() {
        if (!this.vPade && !this.vSkoku) {
            this.obrazok.zmenObrazok("images/" + this.nazov + "/krok/" + this.smer + "/" + this.animacia + ".png");
            if (this.animacia < this.pocetObrazkov) {
                this.animacia++;
            } else {
                this.animacia = 1;
            }
        } else {
            this.obrazok.zmenObrazok("images/" + this.nazov + "/skok/" + this.smer + "/" + this.animaciaSkok + ".png");
        }
    }

    /**
     *
     * @param kolko o kolko sa ma zmenit zdravie
     * @return
     */
    public boolean upravHp(int kolko) { // AK HP KLESNE POD 0, VRÁTI TRUE
        int noveHp = this.hpBar.getHp() + kolko;
        if (noveHp > 0) {
            this.hpBar.upravHp(kolko);
            return false;
        }
        return true;
    }

    public void setSkokPocitadlo(int skokPocitadlo) {
        this.skokPocitadlo = skokPocitadlo;
    }

    public void setvSkoku(boolean vSkoku) {
        this.vSkoku = vSkoku;
    }

    protected void setSmer(Smer smer) {
        this.smer = smer;
    }

    protected void setvPade(boolean vPade) {
        this.vPade = vPade;
    }

    public boolean isvSkoku() {
        return this.vSkoku;
    }

    public boolean isvPade() {
        return this.vPade;
    }

    public void skry() {
        this.obrazok.skry();
        this.getHpBar().skry();
    }
}
