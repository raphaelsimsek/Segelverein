import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.*;

/**
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-12
 */
public class Test_Connector {
    public static void main (String[]args){
        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setServerName("172.16.44.128");
        ds.setPortNumber(5432);
        ds.setUser("schoko");
        ds.setPassword("schoko");
        ds.setDatabaseName("schokoladenfabrik");

        // Verbindung herstellen
        try {
            Connection con = ds.getConnection();
            DatabaseMetaData metaData = con.getMetaData();

            ResultSet rsTables = metaData.getTables(null, null, null, null);

            // Abfrage vorbereiten und ausführen
            Statement st = null;
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM auftrag");
            String wert="";
            // Ergebnisse verarbeiten
            while (rs.next()) { // Cursor bewegen
                wert = rs.getString(1);
            }
            System.out.print(wert);

            // aufraeumen
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}