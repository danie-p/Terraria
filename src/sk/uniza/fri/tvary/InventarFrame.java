package sk.uniza.fri.tvary;

import sk.uniza.fri.bytosti.Hrac;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

/**
 * Okno znaciace inventar hraca, obsahuje jeho polozky a informacie k nim.
 *  *
 * @author danie
 */
public class InventarFrame extends JFrame {

    private final ComboBoxPanel panelComboBox;

    public InventarFrame(Hrac hrac) throws HeadlessException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        this.setTitle("Inventár");
        this.setResizable(false);
        this.setSize(3 * Platno.SIRKA_PLATNA / 4, 3 * Platno.VYSKA_PLATNA / 4);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);
        this.setAlwaysOnTop(true);
        this.setLayout(new BorderLayout());

        this.panelComboBox = new ComboBoxPanel(new FlowLayout(), hrac);
        this.add(this.panelComboBox, BorderLayout.NORTH);

        JPanel panel = new TabulkaPanel(new GridBagLayout(), hrac);
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
        this.toFront();
    }
}
