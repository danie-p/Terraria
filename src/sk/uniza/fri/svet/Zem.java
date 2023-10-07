package sk.uniza.fri.svet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda Zem uchovava informacie o vsetkych polickach v zemi
 * Aktualizuje sa a poskytuje dolezite informacie bytostiam, aby vedeli, ako sa maju spravat a kam sa mozu pohnut
 *
 * @author Daniela Pavlikova
 */
public class Zem {

    public static final int MAX_SIRKA_ZEME = 32;
    public static final int MAX_VYSKA_ZEME = 15;
    public static final int MIN_VYSKA_ZEME_INIT = MAX_VYSKA_ZEME / 2;
    private static final int MIN_VYSKA_ZEME = 1;
    private static final int VYSKOVE_OBMEDZENIE = 3;

    private Policko[][] policka;
    private ArrayList<Policko> neprazdnePolicka;

    /**
     * V konstruktore sa nahodne vygeneruje svet s polickami s roznou pravdepodobnostou a urovnou vyskytu
     */
    public Zem() {
        this.policka = new Policko[Zem.MAX_VYSKA_ZEME][Zem.MAX_SIRKA_ZEME];

        Random generatorVyskyStlpca = new Random();
        Random generatorVyskytuPolicka = new Random();

        int vyskaStlpca = Zem.MIN_VYSKA_ZEME_INIT;

        for (int stlpec = 0; stlpec < this.policka[0].length; stlpec++) {
            int vyskaStlpcaNasl;
            if (stlpec == 0) {
                vyskaStlpcaNasl = Zem.MIN_VYSKA_ZEME_INIT;
            } else {
                vyskaStlpcaNasl = vyskaStlpca + generatorVyskyStlpca.nextInt(2 * Zem.VYSKOVE_OBMEDZENIE) - Zem.VYSKOVE_OBMEDZENIE;
                while (Zem.MAX_VYSKA_ZEME - vyskaStlpcaNasl < Zem.MIN_VYSKA_ZEME || Zem.MAX_VYSKA_ZEME - vyskaStlpcaNasl >= Zem.MAX_VYSKA_ZEME - Zem.MIN_VYSKA_ZEME) {
                    vyskaStlpcaNasl = vyskaStlpca + generatorVyskyStlpca.nextInt(2 * Zem.VYSKOVE_OBMEDZENIE) - Zem.VYSKOVE_OBMEDZENIE;
                }
            }
            for (int riadok = 0; riadok < this.policka.length; riadok++) {
                if (riadok == vyskaStlpcaNasl) {
                    this.policka[riadok][stlpec] = new Policko(riadok, stlpec, TypPolicka.TRAVA);
                } else if (riadok > vyskaStlpcaNasl) {
                    this.policka[riadok][stlpec] = new Policko(riadok, stlpec, TypPolicka.HLINA);
                    for (int i = 0; i < TypPolicka.values().length; i++) {
                        if (riadok >= Zem.MAX_VYSKA_ZEME - TypPolicka.values()[i].getUroven()) {
                            int vyskytPolicka = generatorVyskytuPolicka.nextInt(TypPolicka.values()[i].getPrVyskytu());
                            if (vyskytPolicka == 0) {
                                this.policka[riadok][stlpec].setTyp(TypPolicka.values()[i]);
                            }
                        }
                    }
                } else {
                    this.policka[riadok][stlpec] = new Policko(riadok, stlpec, null);
                }
            }
            vyskaStlpca = vyskaStlpcaNasl;
        }

        this.neprazdnePolicka = new ArrayList<>();
        for (int riadok = 0; riadok < this.policka.length; riadok++) {
            for (int stlpec = 0; stlpec < this.policka[riadok].length; stlpec++) {
                if (this.existujeTypPolicka(riadok, stlpec)) {
                    try {
                        this.neprazdnePolicka.add(this.dajPolicko(riadok, stlpec));
                    } catch (NeexistujucePolickoException e) {
                    }
                }
            }
        }
    }

    /**
     * Vrati povrch, po ktorom sa bytosti mozu pohybovat
     * @return
     */
    public ArrayList<Policko> dajPovrch() {
        ArrayList<Policko> povrch = new ArrayList<>();
        for (int stlpec = 0; stlpec < this.policka[0].length; stlpec++) {
            for (int riadok = 0; riadok < this.policka.length; riadok++) {
                if (this.existujeTypPolicka(riadok, stlpec) && !this.existujeTypPolicka(riadok - 1, stlpec)) {
                    try {
                        povrch.add(this.dajPolicko(riadok, stlpec));
                    } catch (NeexistujucePolickoException e) {
                    }
                }
            }
        }
        return povrch;
    }

    public ArrayList<Policko> dajNadpovrch() {
        ArrayList<Policko> nadpovrch = new ArrayList<>();
        for (int stlpec = 0; stlpec < this.policka[0].length; stlpec++) {
            for (int riadok = 0; riadok < this.policka.length; riadok++) {
                if (!this.existujeTypPolicka(riadok, stlpec) && (this.policka[riadok][stlpec].getRiadok() == Zem.MAX_VYSKA_ZEME - 1 || this.existujeTypPolicka(riadok + 1, stlpec))) {
                    try {
                        nadpovrch.add(this.dajPolicko(riadok, stlpec));
                    } catch (NeexistujucePolickoException e) {
                        System.out.println();
                    }
                }
            }
        }
        return nadpovrch;
    }

    public void pridajNeprazdnePolicko(Policko policko) {
        if (!this.neprazdnePolicka.contains(policko)) {
            this.neprazdnePolicka.add(policko);
        }
    }

    public void odoberNeprazdnePolicko(Policko policko) {
        if (this.neprazdnePolicka.contains(policko)) {
            this.neprazdnePolicka.remove(policko);
        }
    }

    public ArrayList<Policko> getNeprazdnePolicka() {
        return this.neprazdnePolicka;
    }

    public Policko dajPolicko(int riadok, int stlpec) throws NeexistujucePolickoException {
        if (riadok >= 0 && riadok < this.policka.length && stlpec >= 0 && stlpec < this.policka[0].length) {
            return this.policka[riadok][stlpec];
        }
        throw new NeexistujucePolickoException();
    }

    /**
     * Vrati policko zeme na zaklade suradnic
     * @param x suradnica sirky
     * @param y suradnica vysky
     * @return
     * @throws NeexistujucePolickoException
     */
    public Policko dajPolickoXY(int x, int y) throws NeexistujucePolickoException {
        for (Policko[] riadok : this.policka) {
            for (Policko policko : riadok) {
                int polickoX = policko.getStlpec() * Policko.VELKOST_POLICKA;
                int polickoY = policko.getRiadok() * Policko.VELKOST_POLICKA;
                if (x >= polickoX && x <= polickoX + Policko.VELKOST_POLICKA && y >= polickoY && y <= polickoY + Policko.VELKOST_POLICKA) {
                    return policko;
                }
            }
        }
        throw new NeexistujucePolickoException();
    }

    public void nastavTypPolicka(Policko policko, TypPolicka typPolicka) {
        this.policka[policko.getRiadok()][policko.getStlpec()].setTyp(typPolicka);
    }

    public void zobraz() {
        for (Policko[] riadok : this.policka) {
            for (Policko policko : riadok) {
                policko.zobraz();
            }
        }
    }

    public boolean existujeTypPolicka(int riadok, int stlpec) {
        try {
            if (this.dajPolicko(riadok, stlpec).getTyp() == null) {
                return false;
            }
        } catch (NeexistujucePolickoException e) {
            return false;
        }
        return true;
    }
}
