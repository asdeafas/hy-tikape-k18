package tikape.minifoorumi.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import tikape.minifoorumi.database.Database;
import tikape.minifoorumi.domain.Viestiketju;
import tikape.minifoorumi.domain.Viesti;

public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;

    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Viestiketju findOne(Integer id) throws SQLException {
        Connection conn = database.getConnection();

        ViestiDao vdao = new ViestiDao(database);

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viestiketju WHERE Viestiketju.id=?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        Viestiketju viestiketju = null;
        List<Viesti> viestit = vdao.findAllInThread(id);

        while (rs.next()) {

            viestiketju = new Viestiketju(rs.getInt("id"), rs.getString("nimi"), viestit);
        }

        rs.close();
        stmt.close();

        conn.close();

        return viestiketju;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {
        Connection conn = database.getConnection();

        ViestiDao vdao = new ViestiDao(database);

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();

        List<Viestiketju> viestiketjut = new ArrayList<>();

        while (rs.next()) {
            List<Viesti> viestit = vdao.findAllInThread(rs.getInt("id"));
            Viestiketju viestiketju = new Viestiketju(rs.getInt("id"), rs.getString("nimi"), viestit);
            viestiketjut.add(viestiketju);
        }

        rs.close();
        stmt.close();

        conn.close();

        return viestiketjut;
    }

    @Override
    public Viestiketju saveOrUpdate(Viestiketju viestiketju) throws SQLException {
        if (viestiketju.getId() == null) {
            return this.save(viestiketju);
        }
        return this.update(viestiketju);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
//        Connection conn = database.getConnection();
//        ViestiDao vdao = new ViestiDao(database);
//        
//        List<Viesti> viestit = vdao.findAllInThread(id);
//        
//            viestit.stream().forEach(v -> vdao.delete(v.getId()));
//
//        
//        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Viestiketju WHERE Viestiketju.id=?");
//        stmt.setInt(1, id);
//
//        stmt.executeUpdate();
//
//        stmt.close();
//        conn.close();
    }

    private Viestiketju update(Viestiketju viestiketju) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Viestiketju save(Viestiketju viestiketju) throws SQLException {
        Connection conn = database.getConnection();

        ViestiDao vdao = new ViestiDao(database);

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Viestiketju (nimi) "
                + "VALUES (?)");
        stmt.setString(1, viestiketju.getNimi());

        stmt.executeUpdate();

        stmt.close();

        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Viestiketju WHERE Viestiketju.nimi=?;");
        stmt1.setString(1, viestiketju.getNimi());

        ResultSet rs = stmt1.executeQuery();

        Integer vk_id = rs.getInt("id");

        List<Viesti> viestit = vdao.findAllInThread(vk_id);

        Viestiketju palautusketju = new Viestiketju();

        while (rs.next()) {
            palautusketju.setId(vk_id);
            palautusketju.setViestit(viestit);
            palautusketju.setNimi(rs.getString("nimi"));
        }

        rs.close();
        stmt1.close();

        conn.close();

        return palautusketju;

    }
}
