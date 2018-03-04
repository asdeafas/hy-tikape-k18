
package tikape.minifoorumi.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import tikape.minifoorumi.database.Database;
import tikape.minifoorumi.domain.Viestiketju;

public class ViestiketjuDao implements Dao<Viestiketju,Integer> {
    
    private Database database;
    
    public ViestiketjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Viestiketju saveOrUpdate(Viestiketju object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
