package sk.uniza.fri.tvary;

import sk.uniza.fri.bytosti.Hrac;
import sk.uniza.fri.predmety.Jedlo;
import sk.uniza.fri.predmety.Nastroj;
import sk.uniza.fri.predmety.Polozka;
import sk.uniza.fri.predmety.Predmet;
import sk.uniza.fri.predmety.Zbran;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Panel zobrazujuci tabulku s polozkami v inventari, ich informaciami a moznymi akciami
 *
 * @author Daniela Pavlikova
 */
public class TabulkaPanel extends JPanel implements ActionListener {

    private final ArrayList<JButton> buttony;
    private final Hrac hrac;

    public TabulkaPanel(LayoutManager layout, Hrac hrac) {
        super(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        this.buttony = new ArrayList<>();
        this.hrac = hrac;

        // NADPISY
        String[] text = new String[] {"", "Názov položky", "Hmotnosť", "Počet kusov", "Info", "Zahoď"};
        String[] farbicky = new String[] {"#2EB132", "#2EB160", "#2EB181", "#2EB1A9", "#2E9CB1", "#2E88B1"};
        for (int i = 0; i < 6; i++) {
            JLabel label = new JLabel(text[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 15));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBackground(Color.decode(farbicky[i]));
            label.setOpaque(true);
            label.setPreferredSize(new Dimension(1, 1));
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            if (i == 5) {
                gbc.weightx = 3.0;
                gbc.weighty = 1.0;
            } else if (i == 0) {
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
            } else {
                gbc.weightx = 2.0;
                gbc.weighty = 1.0;
            }
            this.add(label, gbc);
        }

        for (int i = 0; i < hrac.dajVelkostInventaru(); i++) {
            Predmet predmet = hrac.dajPolozkuSIndexom(i).getPredmet();

            String[] text2 = new String[] {"", predmet.getNazov(), String.valueOf(predmet.getHmotnost()), String.valueOf(hrac.dajPolozkuSIndexom(i).getPocetKs())};
            String[] farbicky2 = new String[] {"#6DBB70", "#6DBA8A", "#6DBA9E", "#6DBDB8"};
            for (int j = 0; j < 4; j++) {
                JLabel label = new JLabel(text2[j]);
                if (j == 0) {
                    ImageIcon icon = new ImageIcon("images/predmety/" + predmet.getNazov() + ".png");
                    Image image = icon.getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                    label.setIcon(icon);
                }
                label.setBackground(Color.decode(farbicky2[j]));
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(1, 1));
                gbc.gridx = j;
                gbc.gridy = i + 1;
                gbc.gridheight = 1;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                this.add(label, gbc);
            }

            JLabel infoLabel;
            if (predmet instanceof Jedlo) {
                infoLabel = new JLabel("Pridá " + ((Jedlo)predmet).getPridaHp() + " zdravia");
            } else if (predmet instanceof Nastroj) {
                infoLabel = new JLabel("Ostávajúca životnosť: " + ((Nastroj)predmet).getZivotnost());
            } else if (predmet instanceof Zbran) {
                infoLabel = new JLabel("Útočná sila: " + ((Zbran)predmet).getUtocnaSila());
            } else {
                infoLabel = new JLabel();
            }
            infoLabel.setBackground(Color.decode("#6CAEBB"));
            infoLabel.setOpaque(true);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setPreferredSize(new Dimension(1, 1));
            gbc.gridx = 4;
            gbc.gridy = i + 1;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            this.add(infoLabel, gbc);

            JButton zahodBt = new JButton("Zahoď predmet " + predmet.getNazov());
            zahodBt.setBackground(Color.decode("#6DA4BC"));
            zahodBt.setPreferredSize(new Dimension(1, 1));
            gbc.gridx = 5;
            gbc.gridy = i + 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            this.add(zahodBt, gbc);
            this.buttony.add(zahodBt);
        }

        for (JButton jButton : this.buttony) {
            jButton.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton jButton : this.buttony) {
            if (e.getSource().equals(jButton)) {
                for (int i = 0; i < this.hrac.dajVelkostInventaru(); i++) {
                    Polozka polozka = this.hrac.dajPolozkuSIndexom(i);
                    if (jButton.getText().contains(polozka.getPredmet().getNazov())) {
                        this.hrac.odoberZInventara(polozka.getPredmet());
                    }
                }
            }
        }
    }
}
