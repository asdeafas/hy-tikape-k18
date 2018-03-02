import java.sql.*;

public class Tietokantatransaktio {

    public static void main(String[] args) {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt1 = null, pstmt2 = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:" + "test.db");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String material = "goo";
        int warehouseId = 42;
        double qty = 101.34;
       
        try {
       
            conn.setAutoCommit(false);
            
            pstmt1 = conn.prepareStatement("INSERT INTO materials (description) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, material);
            
            int rowAffected = pstmt1.executeUpdate();
            
            rs = pstmt1.getGeneratedKeys();
            int materialId = 0;
            if (rs.next()) {
                materialId = rs.getInt(1);
            }
            
            if (rowAffected != 1) {
                conn.rollback();
            }
            
            pstmt2 = conn.prepareStatement("INSERT INTO inventory(warehouse_id,material_id,qty)"
                    + "VALUES (?,?,?)");
            pstmt2.setInt(1, warehouseId);
            pstmt2.setInt(2, materialId);
            pstmt2.setDouble(3, qty);
            
            pstmt2.executeUpdate();
            
            conn.commit();
            
        } catch (SQLException e1) {
            try {
                if (conn !=null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

}
