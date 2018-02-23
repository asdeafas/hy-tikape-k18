
import java.io.RandomAccessFile;
import java.util.List;

public class Tallentaja {

    public void tallennaHenkilot(List<Henkilo> henkilot, String tiedosto) throws Throwable {

        RandomAccessFile raf = new RandomAccessFile(tiedosto, "rwd");
        raf.seek(0);

        for (Henkilo henkilo : henkilot) {
            raf.writeBytes(varmistaKoko(henkilo.kayttajatunnus, 8));
            raf.writeBytes(varmistaKoko(henkilo.salasana, 8));
            raf.writeBytes(varmistaKoko(henkilo.nimi, 30));
            raf.writeBytes(varmistaKoko(henkilo.osoite, 30));
            raf.writeBytes(varmistaKoko(henkilo.puhelinnumero, 15));
        };

        raf.close();
    }

    public void korvaaHenkilo(String kayttajatunnus, Henkilo henkilo, String tiedosto) throws Throwable {
        // toteuta tehtävässä toivottu toiminnallisuus tänne
        
    }

    // ei kovin tehokas ratkaisu..
    private String varmistaKoko(String merkkijono, int pituus) {
        if (merkkijono.length() > pituus) {
            return merkkijono.substring(0, pituus);
        }

        while (merkkijono.length() < pituus) {
            merkkijono = merkkijono + " ";
        }

        return merkkijono;
    }
}
