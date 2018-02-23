package tehokkuus;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OperaatioidenTehokkuus {

    public static void main(String[] args) throws Exception {

        // luodaan lisättävät oliot
        Generaattori generaattori = new Generaattori();
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new Data(generaattori.ip(), generaattori.address(), generaattori.operation(), generaattori.user(), generaattori.device(), generaattori.date()));

        }

        // operaation keston mittaaminen onnistuu System.nanotime()-metodilla.
        // testaa tietokantaa 1
        File dbFile1 = new File("db", "tapahtumat-denormalisoitu.db");

        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile1.getAbsolutePath());

        long alku1 = System.nanoTime();
        for (Data rivi : data) {
            // tänne lisäystoiminnallisuus kantaan tapahtumat-denormalisoitu.db

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tapahtuma (ip,osoite,operaatio,kayttajatunnus,laite,aika) VALUES ("
                    + "?,?,?,?,?,?)");
            stmt.setString(1, rivi.getIp());
            stmt.setString(2,rivi.getAddress());
            stmt.setString(3, rivi.getOperation());
            stmt.setString(4, rivi.getUser());
            stmt.setString(5, rivi.getDevice());
            int aika = (int) rivi.getDate().getTime();
            stmt.setInt(6,aika);

            stmt.executeUpdate();

            stmt.close();

        }

        conn.close();

        
        long loppu1 = System.nanoTime();
        System.out.println("Tiedon lisääminen tietokantaan 1 kesti " + (loppu1 - alku1) + " nanosekuntia.");

        // testaa tietokantaa 2
        File dbFile2 = new File("db", "tapahtumat-normalisoitu.db");

        Connection conn2 = DriverManager.getConnection("jdbc:sqlite:" + dbFile2.getAbsolutePath());

        long alku2 = System.nanoTime();
        for (Data rivi : data) {
            // tänne lisäystoiminnallisuus kantaan tapahtumat-normalisoitu.db
            String userName = rivi.getUser();
            
            PreparedStatement userStmt = conn2.prepareStatement("SELECT Kayttaja.id FROM Kayttaja WHERE Kayttaja.kayttajatunnus=?");
            userStmt.setString(1, userName);

            ResultSet rs = userStmt.executeQuery();

            int userId= -1;
            
            if (!rs.next()) {
                PreparedStatement createUser = conn2.prepareStatement("INSERT INTO Kayttaja (kayttajatunnus) VALUES (?)");
                createUser.setString(1, userName);

                createUser.executeUpdate();
                createUser.close();

                PreparedStatement idStmt = conn2.prepareStatement("SELECT Kayttaja.id FROM Kayttaja WHERE Kayttaja.kayttajatunnus=?");
                idStmt.setString(1, userName);

                ResultSet rs2 = idStmt.executeQuery();
                
                
                userId = rs2.getInt("id");
                rs2.close();
                idStmt.close();
            }
            else {
                userId = rs.getInt("id");
            }
            
   
            userStmt.close();
            rs.close();
           
            
            String pageAddress = rivi.getAddress();
            
            PreparedStatement pageStmt = conn2.prepareStatement("SELECT Sivu.id FROM Sivu WHERE Sivu.osoite=?");
            pageStmt.setString(1, pageAddress);

            rs = pageStmt.executeQuery();
            
            int pageId=-1;

            if (!rs.next()) {
                PreparedStatement createPage = conn2.prepareStatement("INSERT INTO Sivu (osoite) VALUES (?)");
                createPage.setString(1, pageAddress);

                createPage.executeUpdate();
                createPage.close();

                PreparedStatement idStmt = conn2.prepareStatement("SELECT Sivu.id FROM Sivu WHERE Sivu.osoite=?");
                idStmt.setString(1, pageAddress);

                ResultSet rs2 = idStmt.executeQuery();
                
                
                pageId = rs2.getInt("id");
                
                rs2.close();
                idStmt.close();
            }
            else {
               pageId = rs.getInt("id"); 
            }
            
            
            pageStmt.close();
            rs.close();

            PreparedStatement stmt2 = conn2.prepareStatement("INSERT INTO Tapahtuma (ip,operaatio,laite,aika,kayttaja_id,sivu_id) VALUES ("
                    + "?,?,?,?,?,?)");
            stmt2.setString(1, rivi.getIp());
            stmt2.setString(2, rivi.getOperation());
            stmt2.setString(3, rivi.getDevice());
            int aika = (int) rivi.getDate().getTime();
            stmt2.setInt(4, aika);
            stmt2.setInt(5, userId);
            stmt2.setInt(6, pageId);

            stmt2.executeUpdate();

            stmt2.close();
        }

        conn2.close();
        long loppu2 = System.nanoTime();

        System.out.println("Tiedon lisääminen tietokantaan 2 kesti " + (loppu2 - alku2) + " nanosekuntia.");

    }

}
