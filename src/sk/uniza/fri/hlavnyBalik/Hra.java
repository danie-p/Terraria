package sk.uniza.fri.hlavnyBalik;

import sk.uniza.fri.bytosti.Bojovnik;
import sk.uniza.fri.bytosti.Bytost;
import sk.uniza.fri.bytosti.BytostAI;
import sk.uniza.fri.bytosti.Carodejnik;
import sk.uniza.fri.bytosti.HpBar;
import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.bytosti.Smer;
import sk.uniza.fri.bytosti.Vila;
import sk.uniza.fri.bytosti.Zviera;
import sk.uniza.fri.svet.NeexistujucePolickoException;
import sk.uniza.fri.svet.Policko;
import sk.uniza.fri.svet.Zem;
import sk.uniza.fri.tvary.InventarFrame;
import sk.uniza.fri.tvary.Platno;

import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * V triede Hra sa spajaju vsetky informacie a vytvaraju spolupracujuci celok
 *
 * @author Daniela Pavlikova
 */
public class Hra {

    private final Zem zem;
    private final Hrac hrac;
    private final ArrayList<BytostAI> bytostiAI;
    private boolean bezi;

    /**
     * Konstruktor vytvori a prednastavi hru
     */
    public Hra() {
        JOptionPane.showMessageDialog(Platno.dajPlatno().getFrame(), "Vitaj v Terrarii!\nTerraria je krajina, kde môžeš vlastnoručne\npretvárať svet a spraviť si ho taký, aký chceš.\nPo ceste môžeš natrafiť na kamarátov, ale aj nepriateľov.\nDávaj na seba pozor a bav sa!", "Vitaj!", 1);
        this.zem = new Zem();
        this.hrac = new Hrac(new HpBar(100), 14, 1, "hrac");

        this.bytostiAI = new ArrayList<>();
        this.bytostiAI.add(new Carodejnik());
        this.bytostiAI.add(new Vila());
        this.bytostiAI.add(new Bojovnik());
        this.bytostiAI.add(new Zviera());

        ArrayList<Integer> volneStlpce = new ArrayList<>();
        for (Policko policko : this.zem.dajPovrch()) {
            volneStlpce.add(policko.getStlpec());
        }
        volneStlpce.remove((Integer)0);

        for (int i = 0; i < this.bytostiAI.size(); i++) {
            BytostAI bytost = this.bytostiAI.get(i);
            int nahodnyStlpec = new Random().nextInt(Zem.MAX_SIRKA_ZEME) + 1;
            while (!volneStlpce.contains(nahodnyStlpec)) {
                nahodnyStlpec = new Random().nextInt(Zem.MAX_SIRKA_ZEME) + 1;
            }
            volneStlpce.remove((Integer)nahodnyStlpec);
            if (!(bytost instanceof Bojovnik)) {
                bytost.posunNa(nahodnyStlpec * Policko.VELKOST_POLICKA + ((Policko.VELKOST_POLICKA - bytost.getSirka()) / 2), 0);
            } else {
                bytost.posunNa(nahodnyStlpec * Policko.VELKOST_POLICKA, 0);
            }
            try {
                Policko policko = this.zem.dajPolickoXY(bytost.getX() + 2, bytost.getY() + 2);
                bytost.setPolicko(policko);
                this.zem.pridajNeprazdnePolicko(policko);
            } catch (NeexistujucePolickoException e) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Chyba pri pozastavení threadu");
                }
            }
        }
        this.hraj();
    }

    /**
     * Metoda hraj sa vykonava v cykle, az pokym hrac neprehra.
     * Reaguje na zmeny sveta a vstupy od uzivatela.
     * Obsahuje polymorfne spravanie, pohyby bytosti.
     */
    public void hraj() {
        Platno.dajPlatno().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Bytost odstranena = null;
                try {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_D:
                            if (!Hra.this.hrac.narazi(Smer.DOPRAVA, Hra.this.zem.getNeprazdnePolicka())) {
                                Hra.this.hrac.krok(Smer.DOPRAVA, Bytost.VZDIALENOST_POHYBU);
                            } else {
                                Hra.this.hrac.krok(Smer.DOPRAVA, 0);
                            }
                            break;
                        case KeyEvent.VK_A:
                            if (!Hra.this.hrac.narazi(Smer.DOLAVA, Hra.this.zem.getNeprazdnePolicka())) {
                                Hra.this.hrac.krok(Smer.DOLAVA, Bytost.VZDIALENOST_POHYBU);
                            } else {
                                Hra.this.hrac.krok(Smer.DOLAVA, 0);
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (Hra.this.hrac.getSmer() == Smer.DOPRAVA) {
                                odstranena = Hra.this.hrac.tuk(Hra.this.zem.dajPolickoXY(Hra.this.hrac.dajXVpravo(), Hra.this.hrac.dajPolY()), Hra.this.bytostiAI, Hra.this.zem);
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (Hra.this.hrac.getSmer() == Smer.DOLAVA) {
                                odstranena = Hra.this.hrac.tuk(Hra.this.zem.dajPolickoXY(Hra.this.hrac.dajXVlavo(), Hra.this.hrac.dajPolY()), Hra.this.bytostiAI, Hra.this.zem);
                            }
                            break;
                        case KeyEvent.VK_UP:
                            odstranena = Hra.this.hrac.tuk(Hra.this.zem.dajPolickoXY(Hra.this.hrac.dajPolX(), Hra.this.hrac.dajYHore()), Hra.this.bytostiAI, Hra.this.zem);
                            break;
                        case KeyEvent.VK_DOWN:
                            odstranena = Hra.this.hrac.tuk(Hra.this.zem.dajPolickoXY(Hra.this.hrac.dajPolX(), Hra.this.hrac.dajYDole()), Hra.this.bytostiAI, Hra.this.zem);
                            break;
                        case KeyEvent.VK_E:
                            new InventarFrame(Hra.this.hrac);
                            break;
                    }
                } catch (NeexistujucePolickoException ex) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException exc) {
                        System.err.println("Chyba pri pozastavení threadu");
                    }
                }
                if (odstranena != null) {
                    if (odstranena instanceof BytostAI) {
                        int stlpec = 0;
                        try {
                            Hra.this.zem.odoberNeprazdnePolicko(((BytostAI)odstranena).getPolicko());
                            Hra.this.bytostiAI.remove(odstranena);
                            Thread.sleep(100);
                            odstranena.skry();
                            stlpec = ((BytostAI)odstranena).getPolicko().getStlpec();
                        } catch (NeexistujucePolickoException ex) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException exc) {
                                System.err.println("Chyba pri pozastavení threadu");
                            }
                        } catch (InterruptedException ex) {
                            System.err.println("Chyba pri pozastavení threadu");
                        }
                        BytostAI pridavana;
                        if (odstranena instanceof Bojovnik) {
                            pridavana = new Bojovnik();
                        } else if (odstranena instanceof Carodejnik) {
                            pridavana = new Carodejnik();
                        } else if (odstranena instanceof Vila) {
                            pridavana = new Vila();
                        } else {
                            pridavana = new Zviera();
                        }
                        Hra.this.bytostiAI.add(pridavana);

                        if (!(pridavana instanceof Bojovnik)) {
                            pridavana.posunNa(stlpec * Policko.VELKOST_POLICKA + ((Policko.VELKOST_POLICKA - pridavana.getSirka()) / 2), 0);
                        } else {
                            pridavana.posunNa(stlpec * Policko.VELKOST_POLICKA, 0);
                        }
                        try {
                            Hra.this.bezi = false;
                            Policko policko = Hra.this.zem.dajPolickoXY(pridavana.getX() + 2, pridavana.getY() + 2);
                            pridavana.setPolicko(policko);
                            Hra.this.zem.pridajNeprazdnePolicko(policko);
                            Hra.this.bezi = true;
                        } catch (NeexistujucePolickoException ex) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException exc) {
                                System.err.println("Chyba pri pozastavení threadu");
                            }
                        }

                    } else if (odstranena instanceof Hrac) {
                        Hra.this.bezi = false;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_W:
                        if (!Hra.this.hrac.isvPade() && !Hra.this.hrac.isvSkoku() && !Hra.this.hrac.narazi(Smer.HORE, Hra.this.zem.getNeprazdnePolicka())) {
                            Hra.this.hrac.setvSkoku(true);
                        }
                        break;
                }
            }
        });

        this.bezi = true;

        while (this.bezi) {
            if (!this.hrac.maVInventari(this.hrac.getPredmetVRuke())) {
                this.hrac.setPredmetVRuke(null);
            }
            if (this.hrac.stojiNaPolicku(this.zem.getNeprazdnePolicka()) == null && !this.hrac.isvSkoku()) {
                this.hrac.pad();
                if (this.hrac.stojiNaPolicku(this.zem.getNeprazdnePolicka()) != null) {
                    this.hrac.initObrazok();
                }
            }
            // POLYMORFIZMUS
            ArrayList<Policko> nullovyNadpovrch = this.zem.dajNadpovrch();
            for (int i = 0; i < this.bytostiAI.size(); i++) {
                try {
                    nullovyNadpovrch.remove(this.zem.dajPolicko(this.bytostiAI.get(i).getPolicko().getRiadok(), this.bytostiAI.get(i).getPolicko().getStlpec()));
                    this.zem.pridajNeprazdnePolicko(this.bytostiAI.get(i).getPolicko());
                } catch (NeexistujucePolickoException e) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        System.err.println("Chyba pri pozastavení threadu");
                    }
                }
            }
            for (int i = 0; i < this.bytostiAI.size(); i++) {
                BytostAI bytostAI = this.bytostiAI.get(i);
                try {
                    this.zem.odoberNeprazdnePolicko(bytostAI.getPolicko());
                    Policko policko = this.zem.dajPolickoXY(bytostAI.getX() + 2, bytostAI.getY() + 2);
                    bytostAI.setPolicko(policko);
                    this.zem.pridajNeprazdnePolicko(policko);
                } catch (NeexistujucePolickoException e) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        System.err.println("Chyba pri pozastavení threadu");
                    }
                }
                if (bytostAI.stojiNaPolicku(this.zem.dajPovrch()) == null && !bytostAI.isvSkoku()) {
                    bytostAI.pad();
                    if (bytostAI.stojiNaPolicku(this.zem.dajPovrch()) != null) {
                        bytostAI.initObrazok();
                    }
                } else {
                    bytostAI.krok();
                    bytostAI.pohybuj(nullovyNadpovrch, this.zem);
                }
            }
            if (this.hrac.isvSkoku()) {
                this.hrac.skok();
                if (!this.hrac.isvSkoku()) {
                    this.hrac.setSkokPocitadlo(0);
                }
            }
        }
        JOptionPane.showMessageDialog(Platno.dajPlatno().getFrame(), "Prehra\nToto je koniec tvojej púte Terrariou", "Koniec!", 1);
        System.exit(0);
    }
}