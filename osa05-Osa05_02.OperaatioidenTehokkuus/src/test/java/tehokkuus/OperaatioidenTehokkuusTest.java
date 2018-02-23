package tehokkuus;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("05-02")
public class OperaatioidenTehokkuusTest {

    @Test
    public void tietokantaTiedostotOlemassa() {
        assertTrue(new File("db", "tapahtumat-normalisoitu.db").exists());
        assertTrue(new File("db", "tapahtumat-denormalisoitu.db").exists());
    }

    @Test
    public void normalisoidunTietokannanSarakkeetOlemassa() throws SQLException {
        File tietokantatiedosto = new File("db", "tapahtumat-normalisoitu.db");

        onSarake(tietokantatiedosto, "Kayttaja", "id", "integer");
        onSarake(tietokantatiedosto, "Kayttaja", "kayttajatunnus", "varchar");

        onSarake(tietokantatiedosto, "Sivu", "id", "integer");
        onSarake(tietokantatiedosto, "Sivu", "osoite", "varchar");

        onSarake(tietokantatiedosto, "Tapahtuma", "id", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "kayttaja_id", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "sivu_id", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "aika", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "operaatio", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "ip", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "laite", "varchar");
    }

    @Test
    public void denormalisoidunTietokannanSarakkeetOlemassa() throws SQLException {
        File tietokantatiedosto = new File("db", "tapahtumat-denormalisoitu.db");

        onSarake(tietokantatiedosto, "Tapahtuma", "id", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "kayttajatunnus", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "osoite", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "aika", "integer");
        onSarake(tietokantatiedosto, "Tapahtuma", "operaatio", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "ip", "varchar");
        onSarake(tietokantatiedosto, "Tapahtuma", "laite", "varchar");
    }

    @Test
    public void mainMetodissaLisataanDenormalisoituunTietokantaan() throws SQLException {
        File db = new File("db", "tapahtumat-denormalisoitu.db");
        tarkastaRivienLukumaara(db, "Tapahtuma", 100, false);
    }

    @Test
    public void mainMetodissaLisataanNormalisoituunTietokantaan() throws SQLException {
        File db = new File("db", "tapahtumat-normalisoitu.db");
        tarkastaRivienLukumaara(db, "Tapahtuma", 100, false);
        tarkastaRivienLukumaara(db, "Sivu", 100, true);
        tarkastaRivienLukumaara(db, "Kayttaja", 100, true);
    }

    private void tarkastaRivienLukumaara(File tietokanta, String taulu, int odotettu, boolean alle) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + tietokanta.getAbsolutePath())) {
            conn.prepareStatement("DELETE FROM " + taulu).executeUpdate();
        }

        try {
            OperaatioidenTehokkuus.main(new String[]{});
        } catch (Exception e) {

        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + tietokanta.getAbsolutePath())) {
            ResultSet res = conn.prepareStatement("SELECT COUNT(*) AS rows FROM " + taulu).executeQuery();
            res.next();

            if (!alle) {
                assertEquals("OperaatioidenTehokkuus-luokan main-metodissa tulee lisätä " + odotettu + " riviä tietokannan " + tietokanta.getName() + " tauluun " + taulu, odotettu, res.getInt("rows"));
            } else {
                assertTrue("OperaatioidenTehokkuus-luokan main-metodissa tulee lisätä alle " + odotettu + " riviä tietokannan " + tietokanta.getName() + " tauluun " + taulu, odotettu > res.getInt("rows"));
            }

        }
    }

    public void onSarake(File tietokantatiedosto, String taulu, String sarakkeenNimi, String tyyppi) throws SQLException {
        Optional<Sarake> nimi = haeSarakkeet(taulu, tietokantatiedosto).stream().filter(s -> s.nimi.toLowerCase().equals(sarakkeenNimi)).findFirst();
        if (!nimi.isPresent()) {
            fail("Tiedoston " + tietokantatiedosto + " taulusta " + taulu + " puuttuu sarake nimeltä \"" + sarakkeenNimi + "\".");
        }

        if (!nimi.get().tyyppi.toLowerCase().trim().equals(tyyppi)) {
            fail("Tiedoston " + tietokantatiedosto + " taulun " + taulu + " sarakkeen tyypin piti olla " + tyyppi + ". Nyt tyyppi oli " + nimi.get().tyyppi);
        }
    }

    List<Sarake> haeSarakkeet(String taulu, File tietokanta) throws SQLException {
        assertTrue(tietokanta.exists());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + tietokanta.getAbsolutePath())) {
            ResultSet tulos = conn.prepareStatement("SELECT * FROM " + taulu).executeQuery();
            ResultSetMetaData meta = tulos.getMetaData();

            List<Sarake> sarakkeet = new ArrayList<>();
            for (int i = 0; i < meta.getColumnCount(); i++) {
                Sarake s = new Sarake();
                s.nimi = meta.getColumnName(i + 1);
                s.tyyppi = meta.getColumnTypeName(i + 1);

                sarakkeet.add(s);
            }

            return sarakkeet;
        }

    }

    static class Sarake {

        String nimi;
        String tyyppi;
    }

}
