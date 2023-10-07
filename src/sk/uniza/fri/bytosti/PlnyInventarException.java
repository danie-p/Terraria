package sk.uniza.fri.bytosti;

import sk.uniza.fri.tvary.Platno;

import javax.swing.JOptionPane;

/**
 * Mnou definovana vynimka, ktora sa ukaze pri zaplnenom inventari a informuje o tomto probleme uzivatela
 *
 * @author Daniela Pavlikova
 */
public class PlnyInventarException extends Throwable {

    public PlnyInventarException() {
        JOptionPane.showMessageDialog(Platno.dajPlatno().getFrame(), "Máš plný inventár!");
    }
}
