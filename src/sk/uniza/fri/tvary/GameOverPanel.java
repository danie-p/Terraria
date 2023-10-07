package sk.uniza.fri.tvary;

import sk.uniza.fri.hlavnyBalik.Hra;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameOverPanel nebol v projekte pouzity, nepodarilo sa mi ho, zial, implementovat.
 * Mal by vsak sluzit na oznamenie konca hry a mozny vstup do novej hry.
 *
 * @author Daniela Pavlikova
 */
public class GameOverPanel extends JFrame implements ActionListener {

    public GameOverPanel() {

        this.setBackground(Color.decode("#2EB132"));
        this.setLayout(new BorderLayout());
        this.setSize(Platno.SIRKA_PLATNA, Platno.VYSKA_PLATNA);

        JLabel koniecHry = new JLabel("KONIEC\nHRY");
        koniecHry.setFont(new Font("SansSerif", Font.BOLD, 50));
        koniecHry.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(koniecHry);

        JButton novaHra = new JButton("Nová hra");
        novaHra.setFont(new Font("SansSerif", Font.BOLD, 20));
        novaHra.setHorizontalAlignment(SwingConstants.CENTER);
        novaHra.addActionListener(this);
        this.add(novaHra);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Hra hra = new Hra();
    }
}
