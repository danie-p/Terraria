package sk.uniza.fri.bytosti;

import sk.uniza.fri.IDavajuciLoot;
import sk.uniza.fri.predmety.Polozka;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.predmety.Zbran;
import sk.uniza.fri.svet.NeexistujucePolickoException;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;
import sk.uniza.fri.tvary.Platno;

import java.util.ArrayList;

/**
 * Trieda Hrac zdruzuje vsetky atributy a metody, ktore su potrebne pri ovladani postavy hraca a navigovanie sveta
 * Je potomkom triedy Bytost a implementuje IAgresor
 *
 * @author Daniela Pavlikova
 */
public class Hrac extends Bytost implements IAgresor {

    private int utocnaSila;
    private ArrayList<Polozka> inventar;
    private Predmet predmetVRuke;
    private static final int MAX_HMOTNOST_INVENTARU = 50;

    public Hrac(HpBar hpBar, int pocetObrazkov, int pocetObrazkovSkok, String nazov) {
        super(hpBar, pocetObrazkov, pocetObrazkovSkok, nazov);
        this.inventar = new ArrayList<>();
        this.utocnaSila = 5;
    }

    /**
     * Na zaklade mnoziny neprazdnych policok definuje, ci hrac pri pohybe danym smerom narazi alebo nie
     *
     * @param smer smer, ktorym sa chce hrac pohnut
     * @param neprazdne neprazdne policka zeme
     * @return
     */
    public boolean narazi(Smer smer, ArrayList<Policko> neprazdne) {
        int bod = 0;
        switch (smer) {
            case DOLAVA -> bod = this.getX() - Bytost.VZDIALENOST_POHYBU - 1;
            case DOPRAVA -> bod = this.getX() + this.getSirka() + Bytost.VZDIALENOST_POHYBU + 1;
            case HORE -> bod = this.getY() - Bytost.VZDIALENOST_POHYBU - 1;
            case DOLE -> bod = this.getY() + this.getVyska() + Bytost.VZDIALENOST_POHYBU + 1;
        }
        for (int i = 0; i < neprazdne.size(); i++) {
            Policko policko = neprazdne.get(i);
            if (smer == Smer.DOLAVA || smer == Smer.DOPRAVA) {
                if (bod <= policko.getStlpec() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA &&
                    bod >= policko.getStlpec() * Policko.VELKOST_POLICKA &&
                        ((this.getY() < policko.getRiadok() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA &&
                        this.getY() > policko.getRiadok() * Policko.VELKOST_POLICKA) ||
                        (this.getY() + this.getVyska() <= policko.getRiadok() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA &&
                        this.getY() + this.getVyska() > policko.getRiadok() * Policko.VELKOST_POLICKA))) {
                    return true;
                }
            } else {
                if ((bod <= policko.getRiadok() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA) &&
                    (bod >= policko.getRiadok() * Policko.VELKOST_POLICKA) &&
                        (((this.getX() < policko.getStlpec() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA) &&
                        (this.getX() > policko.getStlpec() * Policko.VELKOST_POLICKA)) ||
                        ((this.getX() + this.getSirka() <= policko.getStlpec() * Policko.VELKOST_POLICKA + Policko.VELKOST_POLICKA) &&
                        (this.getX() + this.getSirka() > policko.getStlpec() * Policko.VELKOST_POLICKA)))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Definuje krok, teda pohnutie hraca
     * @param smer
     * @param vzdialenost
     */
    public void krok(Smer smer, int vzdialenost) {
        super.setSmer(smer);
        super.krok();
        super.getHpBar().posunNa(this.getX(), this.getY() - super.getHpBar().getVyska() - super.getHpBar().getVyska() / 2);
        if (smer == Smer.DOPRAVA && super.getX() + super.getSirka() + vzdialenost <= Platno.SIRKA_PLATNA) {
            super.getObrazok().posunVodorovne(vzdialenost);
        } else if (smer == Smer.DOLAVA && super.getX() - vzdialenost >= 0) {
            super.getObrazok().posunVodorovne(-vzdialenost);
        }
    }

    /**
     * Definuje skok a zmeny pri nom
     */
    public void skok () {
        super.getHpBar().posunNa(this.getX(), this.getY() - super.getHpBar().getVyska() - super.getHpBar().getVyska() / 2);
        if (this.getSkokPocitadlo() < Bytost.VYSKA_SKOKU && (this.getY() - Bytost.VZDIALENOST_POHYBU >= 0)) {
            this.setvPade(false);
            this.setvSkoku(true);
            this.getObrazok().zmenObrazok("images/" + this.getNazov() + "/skok/" + this.getSmer() + "/1.png");
            if (this.getSkokPocitadlo() < Bytost.VYSKA_SKOKU / 2) {
                this.getObrazok().zmenPolohu(this.getX(), this.getY() - 5);
            } else {
                this.getObrazok().zmenPolohu(this.getX(), this.getY() - 3);
            }
            this.setSkokPocitadlo(this.getSkokPocitadlo() + 1);
        } else {
            this.setvSkoku(false);
            this.setvPade(true);
        }
    }

    /**
     * Ak je to mozne, hrac zodvihne predmet a prida ho do inventara
     * Pouziva polymorfizmus
     * @param predmet
     * @return
     * @throws PlnyInventarException
     */
    public boolean zodvihni(Predmet predmet) throws PlnyInventarException {
        if (this.dajHmotnostInventaru() + predmet.getHmotnost() <= Hrac.MAX_HMOTNOST_INVENTARU) {
            if (this.maVInventari(predmet) && this.dajPolozku(predmet) != null) {
                this.dajPolozku(predmet).pridajKus();
            } else {
                this.inventar.add(new Polozka(predmet));
            }
            return true;
        } else {
            throw new PlnyInventarException();
        }
    }

    /**
     * Odoberie polozku z inventara
     * @param predmet
     */
    public void odoberZInventara(Predmet predmet) {
        try {
            if (this.dajPolozku(predmet).getPocetKs() > 1) {
                this.dajPolozku(predmet).odoberKus();
            } else {
                this.inventar.remove(this.dajPolozku(predmet));
                this.setPredmetVRuke(null);
            }
        } catch (IllegalArgumentException iaex) {
            System.err.println("Taký predmet v inventári nemá");
        }
    }

    public boolean maVInventari(String string) {
        for (Polozka polozka : this.inventar) {
            if (string.equals(polozka.getPredmet().getNazov())) {
                return true;
            }
        }
        return false;
    }

    public Predmet dajPredmet(String string) {
        if (maVInventari(string)) {
            for (Polozka polozka : this.inventar) {
                if (string.equals(polozka.getPredmet().getNazov())) {
                    return polozka.getPredmet();
                }
            }
        }
        return null;
    }

    public Polozka dajPolozku(Predmet predmet) {
        if (this.maVInventari(predmet)) {
            for (Polozka polozka : this.inventar) {
                if (polozka.getPredmet().getNazov().equals(predmet.getNazov())) {
                    return polozka;
                }
            }
        }
        return null;
    }

    public boolean maVInventari(Predmet predmet) {
        if (predmet != null) {
            for (Polozka polozka : this.inventar) {
                if (predmet.getNazov().equals(polozka.getPredmet().getNazov())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Polozka dajPolozkuSIndexom(int index) {
        try {
            if (index < 0 || index >= this.inventar.size()) {
                throw new IllegalArgumentException();
            } else {
                return this.inventar.get(index);
            }
        } catch (IllegalArgumentException iaex) {
            System.err.println("Zlý index");
        }
        return null;
    }

    public int dajVelkostInventaru() {
        return this.inventar.size();
    }

    private double dajHmotnostInventaru() {
        double celkovaHmotnost = 0;
        for (Polozka polozka : this.inventar) {
            celkovaHmotnost += polozka.getPredmet().getHmotnost() * polozka.getPocetKs();
        }
        return celkovaHmotnost;
    }

    public Predmet getPredmetVRuke() {
        return this.predmetVRuke;
    }

    public void setPredmetVRuke(Predmet predmet) {
        if (predmet != null) {
            if (this.maVInventari(predmet)) {
                this.predmetVRuke = predmet;
            }
        } else {
            this.predmetVRuke = null;
        }
    }

    public int dajXVpravo() {
        return this.getX() + this.getSirka() + Policko.VELKOST_POLICKA / 2;
    }

    public int dajXVlavo() {
        return this.getX() - Policko.VELKOST_POLICKA / 2;
    }

    public int dajPolY() {
        return this.getY() + Policko.VELKOST_POLICKA / 2;
    }

    public int dajPolX() {
        return this.getX() + Policko.VELKOST_POLICKA / 2;
    }

    public int dajYHore() {
        return this.getY() - Policko.VELKOST_POLICKA / 2;
    }

    public int dajYDole() {
        return this.getY() + this.getVyska() + Policko.VELKOST_POLICKA / 2;
    }

    /**
     * Definuje, co sa stane, ked uzivatel tukne (teda klikne) jednou zo sipok
     * Urcuje interakciu s okolim
     * Obsahuje POLYMORFIZMUS, pricom vykona rozne cinnosti v zavislosti od predmetu, ktory drzi v ruke
     *
     * @param policko
     * @param bytosti
     * @param zem
     * @return
     */
    public Bytost tuk(Policko policko, ArrayList<BytostAI> bytosti, Zem zem) {
        BytostAI bytost = null;
        for (BytostAI bytostAI : bytosti) {
            try {
                if (bytostAI.getPolicko().equals(policko)) {
                    bytost = bytostAI;
                }
            } catch (NeexistujucePolickoException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Chyba pri pozastavení threadu");
                }
            }
        }
        if (this.predmetVRuke != null) {
            this.predmetVRuke.pouzi(this, bytost, policko, zem);    // POLYMORFIZMUS
        } else {
            if (policko.getTyp() != null) {
                this.znic(policko, zem, 1);
            } else if (bytost != null){
                return this.napadni(bytost, this.utocnaSila);
            }
        }
        return null;
    }

    /**
     * Danou silou utoku napadne urcitu bytost, teda sa pokusi znizit jej zdravie
     * @param bytost
     * @param silaUtoku
     * @return
     */
    @Override
    public Bytost napadni(Bytost bytost, int silaUtoku) { // polymorfizmus
        if (bytost.upravHp(-silaUtoku)) {
            if (bytost instanceof IDavajuciLoot) {
                try {
                    this.zodvihni(((IDavajuciLoot)bytost).getLoot());
                } catch (PlnyInventarException e) {
                }
            }
            return bytost;
        }
        if (bytost instanceof IAgresor) {
            if (this.upravHp(-(((IAgresor)bytost).getUtocnaSila()))) {
                return this;
            }
        }
        if (bytost instanceof Vila) {
            this.upravHp(20);
        }
        return null;
    }

    @Override
    public int getUtocnaSila() {
        if (this.predmetVRuke instanceof Zbran) {
            return ((Zbran)this.predmetVRuke).getUtocnaSila();
        }
        return 1;
    }

    /**
     * Postupne znici policko danou niciacou silou
     * @param policko
     * @param zem
     * @param niciacaSila
     */
    public void znic(Policko policko, Zem zem, int niciacaSila) {
        if (policko.getTyp() != null) {
            policko.setOdolnost(policko.getOdolnost() - niciacaSila);
            if (policko.getOdolnost() <= 0) {
                try {
                    this.zodvihni(policko.getLoot());
                } catch (PlnyInventarException e) {
                }
                zem.odoberNeprazdnePolicko(policko);
                zem.nastavTypPolicka(policko, null);
                zem.zobraz();
            }
        }
    }
}
