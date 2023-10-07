package sk.uniza.fri.bytosti;

/**
 * Interface IAgresor urcuje metody napadni a getUtocnaSila, ktore su potrebne pre kazdu bytost, ktora inym ublizuje
 *
 * @author Daniela Pavlikova
 */
public interface IAgresor {
    Bytost napadni(Bytost bytost, int silaUtoku);
    int getUtocnaSila();
}
