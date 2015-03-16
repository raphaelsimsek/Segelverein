import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.*;

/**
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-12
 * A first little testing application to using the PostgreSQL connector and outputting the first row of it's ResultSet
 */
public class Test_Connector {
    public static void main (String[]args){
        PGSimpleDataSource ds = new PGSimpleDataSource();     //PostgreSQL DataSource Class, chose Simple, see documentation
        ds.setServerName("VMware"); //VMware is the name set for the IP adress of the virtual machine on each of my systems
        ds.setPortNumber(5432);     //The port which PostgreSQL uses, see PGadmin3
        ds.setUser("schoko");   //User, which owns the database 'schokoladenfabrik'
        ds.setPassword("schoko");   //Password of the user
        ds.setDatabaseName("schokoladenfabrik");    //Database, which shall be read

        try {
            Connection con = ds.getConnection();    //connecting with the DataSource to its database
            con.setAutoCommit(false);   //deactivating autocommit, transactions are therefor enabled
            DatabaseMetaData metaData = con.getMetaData();  //metadata for

            ResultSet rsTables = metaData.getTables(null, null, null, null);

            Statement st;    //preparing statement
            st = con.createStatement(); //used for options to filter the queries result
            ResultSet rs = st.executeQuery("SELECT * FROM auftrag");
            String value="";
            while (rs.next()) { // moving cursor through rows of the resultSet
                value = rs.getString(1);
            }
            System.out.print(value);

            // Cleaning up and therefor closing all opened JDBC expressions
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}