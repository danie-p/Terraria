package sk.uniza.fri.tvary;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

/**
 * Trieda prebraná z cvičení, upravená pre potreby tohto projektu.
 * Trieda Obrazok, reprezentuje bitmapovy obrazok, ktory moze byt vykresleny na platno.
 * 
 * @author Miroslav Kvassay
 * @authot Michal Varga
 * 
 * @version 1.1
 */
public class Obrazok {
    private int lavyHornyX;
    private int lavyHornyY;
    private int povodnaVelkostX;
    private int povodnaVelkostY;
    private int velkostX;
    private int velkostY;
    private double uhol;
    
    private BufferedImage obrazok;

    /**
     * Parametricky konstruktor vytvori Obrazok na pozicii paX, paY s natocenim paUhol
     * 
     * @param suborSObrazkom cesta k suboru s obrazkom, ktory sa ma vykreslovat
     */
    public Obrazok(String suborSObrazkom, int lavyHornyX, int lavyHornyY) {
        this.obrazok = this.nacitajObrazokZoSuboru(suborSObrazkom);
        this.lavyHornyX = lavyHornyX;
        this.lavyHornyY = lavyHornyY;
        this.povodnaVelkostX = this.obrazok.getWidth();
        this.povodnaVelkostY = this.obrazok.getHeight();
        this.velkostX = this.povodnaVelkostX;
        this.velkostY = this.povodnaVelkostY;
        this.uhol = 0;
        this.zobraz();
    }

    public Obrazok(String suborSObrazkom) {
        this.obrazok = this.nacitajObrazokZoSuboru(suborSObrazkom);
        this.lavyHornyX = 0;
        this.lavyHornyY = 0;
        this.povodnaVelkostX = this.obrazok.getWidth();
        this.povodnaVelkostY = this.obrazok.getHeight();
        this.velkostX = this.povodnaVelkostX;
        this.velkostY = this.povodnaVelkostY;
        this.uhol = 0;
        this.zobraz();
    }
    
    public BufferedImage getObrazok() {
        return this.obrazok;
    }
    
    /**
     * (Obrázok) Zobraz sa.
     */
    public void zobraz() {      
        this.nakresli();
    }
    
    /**
     * (Obrázok) Skry sa.
     */
    public void skry() {       
        this.zmaz();
    }
    
    /**
     * (Obrázok) Vráti polohu X.
     */
    public int getPolohaX() {
        return this.lavyHornyX;
    }
    
    /**
     * (Obrázok) Vráti polohu Y.
     */
    public int getPolohaY() {
        return this.lavyHornyY;
    }

    /**
     * (Obrázok) Posuň sa vodorovne o dĺžku danú parametrom.
     * 
     * @param vzdialenost vzidalenosť o ktorú sa má obrázok posunúť vodorovne
     */
    public void posunVodorovne(int vzdialenost) {
        this.lavyHornyX += vzdialenost;
        this.zobraz();
    }

    /**
     * (Obrázok) Posuň sa zvisle o dĺžku danú parametrom.
     *
     *@param vzdialenost vzdialenosť o ktorú sa obrázok posunie zvisle
     */
    public void posunZvisle(int vzdialenost) {
        this.lavyHornyY += vzdialenost;
        this.zobraz();
    }

    /**
     * (Obrázok) Zmení obrázok.
     * Súbor s obrázkom musí existovať.
     * 
     * @param suborSObrazkom cesta k súboru s obrázkom, ktorý sa má načítať
     */
    public void zmenObrazok(String suborSObrazkom) {
        this.obrazok = this.nacitajObrazokZoSuboru(suborSObrazkom);
        this.zobraz();
    }
    
    /**
     * (Obrázok) Zmeň polohu ľavého horného rohu obrázka na hodnoty dané parametrami a pridá k nim 1 px. 
     */
    public void zmenPolohu(int lavyHornyX, int lavyHornyY) {
        this.lavyHornyX = lavyHornyX;
        this.lavyHornyY = lavyHornyY;
        this.zobraz();
    }
    
    /**
     * (Obrázok) Zmeň uhol natočenia obrázku podľa parametra. Sever = 0.
     * 
     * @param uhol uhol o ktorý sa zmení natočenie obrázku
     */
    public void zmenUhol(double uhol) {
        this.uhol = uhol;
        this.zobraz();
    }
    
    /**
     * (Obrázok) Zmeň veľkosť obrázku podľa parametrov. .
     */
    public void zmenVelkost(int velkostX, int velkostY) {
        this.velkostX = velkostX;
        this.velkostY = velkostY;
        this.zobraz();
    }
    
    /**
     * Načíta obrázok zo súboru.
     */
    private BufferedImage nacitajObrazokZoSuboru(String subor) {
        BufferedImage nacitanyOBrazok = null;
        
        try {
            nacitanyOBrazok = ImageIO.read(new File(subor));
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Subor " + subor + " sa nenasiel.");
        }        
        
        return nacitanyOBrazok;
    }     
    
    /**
     * (Obrázok) Vráti šírku obrázka.
     * 
     * @return velkostX šírka obrázka
     */
    public int sirka() {
        return this.velkostX;
    }
    
    /**
     * (Obrázok) Vráti výšku obrázka.
     * 
     * @return velkostY výška obrázka
     */
    public int vyska() {
        return this.velkostY;
    }    
    
    /*
     * Draw the square with current specifications on screen.
     */
    private void nakresli() {
        Platno canvas = Platno.dajPlatno();

        AffineTransform at = new AffineTransform();
        at.translate(this.lavyHornyX + this.sirka() / 2, this.lavyHornyY + this.vyska() / 2);
        at.rotate(this.uhol / 180.0 * Math.PI);
        at.translate(-this.sirka() / 2, -this.vyska() / 2);
        at.scale(this.velkostX / (double)this.povodnaVelkostX, this.velkostY / (double)this.povodnaVelkostY);

        canvas.draw(this, this.obrazok, at);
    }

    /*
     * Erase the square on screen.
     */
    private void zmaz() {
        Platno canvas = Platno.dajPlatno();
        canvas.erase(this);
    }
    
}
