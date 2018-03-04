
package tikape.minifoorumi.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import tikape.minifoorumi.database.Database;
import tikape.minifoorumi.domain.Viesti;

public class ViestiDao implements Dao<Viesti,Integer>{
    
    private Database database;
    
    public ViestiDao(Database database) {
        this.database=database;
    }

    @Override
    public Viesti findOne(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti WHERE Viesti.id=?");
        stmt.setInt(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        Viesti viesti = null;
        
        while (rs.next()) {
            viesti = new Viesti(rs.getInt("id"),rs.getInt("viestiketju_id"),rs.getString("sanoma"),rs.getInt("jarjestys"));
        }
        
        rs.close();
        stmt.close();
        
        conn.close();
        
        return viesti;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
   Connection conn = database.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Viesti");
        
        ResultSet rs = stmt.executeQuery();
        
        List<Viesti> viestit = new ArrayList<>();
        
        while (rs.next()) {
            Viesti viesti = new Viesti(rs.getInt("id"),rs.getInt("viestiketju_id"),rs.getString("sanoma"),rs.getInt("jarjestys"));
            viestit.add(viesti);
        }
        
        rs.close();
        stmt.close();
        
        conn.close();
        
        return viestit;
    }

    @Override
    public Viesti saveOrUpdate(Viesti viesti) throws SQLException {
        if (viesti.getId()==null) {
            return this.save(viesti);
        }
        return this.update(viesti);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection conn = database.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Viesti WHERE Viesti.id=?");
        stmt.setInt(1, id);
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
    
    
    private Viesti update(Viesti viesti) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Viesti save(Viesti viesti) throws SQLException {
        Connection conn = database.getConnection();
        
        PreparedStatement orderStmt = conn.prepareStatement("SELECT jarjestys FROM Viesti ORDER BY jarjestys LIMIT 1");
        
        ResultSet orderResults = orderStmt.executeQuery();
        
        int jarjestys = 0;
        
        while (orderResults.next()) {
            jarjestys = orderResults.getInt("id");
        }
        jarjestys++;
        
        orderResults.close();
        orderStmt.close();
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Viesti (viestiketju_id,sanoma,jarjestys) "
                + "VALUES (?,?,?)");
        stmt.setInt(1,viesti.getViestiketjuId());
        stmt.setString(2, viesti.getSanoma());
        stmt.setInt(3, jarjestys);
        
        stmt.executeUpdate();
        
        stmt.close();
        
        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM Viesti WHERE Viesti.sanoma=? "
                + "AND Viesti.viestiketju_id=?");
        stmt1.setString(1, viesti.getSanoma());
        stmt1.setInt(2, viesti.getViestiketjuId());
        
        ResultSet rs = stmt1.executeQuery();
        
        while (rs.next()) {
            viesti.setId(rs.getInt("id"));
            viesti.setJarjestys(rs.getInt("jarjestys"));
            viesti.setJarjestys(jarjestys);
        }
        
        rs.close();
        stmt1.close();
        
        conn.close();
        
        return viesti;
       
    }
}
