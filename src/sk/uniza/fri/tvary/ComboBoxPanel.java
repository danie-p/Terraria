package sk.uniza.fri.tvary;

import sk.uniza.fri.bytosti.Hrac;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel, na ktorom je pripnuty ComboBox, vdaka ktoremu sa daju navolit predmety, ktore hrac drzi v ruke
 *
 * @author Daniela Pavlikova
 */
public class ComboBoxPanel extends JPanel implements ActionListener {

    private final JComboBox comboBox;
    private Hrac hrac;

    public ComboBoxPanel(LayoutManager layout, Hrac hrac) {
        super(layout);
        this.hrac = hrac;

        this.setBackground(Color.decode("#F2F38E"));
        String[] polozky = new String[hrac.dajVelkostInventaru() + 1];
        polozky[0] = "NIČ";
        for (int i = 0; i < hrac.dajVelkostInventaru(); i++) {
            polozky[i + 1] = (hrac.dajPolozkuSIndexom(i).getPredmet().getNazov());
        }

        this.comboBox = new JComboBox(polozky);
        this.comboBox.setBackground(Color.decode("#E2E383"));
        this.comboBox.setMaximumRowCount(10);
        this.comboBox.addActionListener(this);

        JLabel uchop = new JLabel("Uchop predmet do ruky");
        uchop.setFont(new Font("SansSerif", Font.BOLD, 15));
        this.add(uchop);
        this.add(this.comboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.comboBox) {
            if (this.comboBox.getSelectedItem().toString().equals("NIČ")) {
                hrac.setPredmetVRuke(null);
            } else {
                hrac.setPredmetVRuke(hrac.dajPredmet(this.comboBox.getSelectedItem().toString()));
            }
        }
    }
}
