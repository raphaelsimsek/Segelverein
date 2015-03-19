package Segelverein;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class is used as the Model for the assignment 'Segelverein'
 * Still in an early state
 * This class builds the connection to the database, for the controller to use.
 * It then uses the connection it built to read the databases' tables.
 *
 * In a future version this shall also control the CRUD commands to the database, though it is already reading the database.
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelModel {
    //creating attributes
    private String server;
    private String user;
    private String pw;
    private String dbname;
    private static Connection con;
    private DatabaseMetaData metaData;


    /**
     * main/default constructor - setting default values for attributes
     */
    public SegelModel(){
        this.server="VMware";
        this.user="schoko";
        this.pw="schoko";
        this.dbname="schokoladenfabrik";
    }

    /**
     * Builds a connection using the JDBC Connector of the PostgreSQL DBMS, with the specified parameters.
     * @param server specifies the databases' IP address or the location of the IP address in the hosts file
     * @param user specifies the user of the database to connect to
     * @param pw specifies the password of the user
     * @param dbname specifies the database, which the connector shall connect to
     * @return returns the built static autocommit-disabled Connection
     * @throws SQLException relays the Exception to the controller, which relays it to the main method
     */
    public Connection getCon(String server, String user, String pw, String dbname) throws SQLException{
        //setting the parameters to the local attributes
        this.server=server;
        this.user=user;
        this.pw=pw;
        this.dbname=dbname;

        PGSimpleDataSource ds = new PGSimpleDataSource();     //PostgreSQL DataSource Class, chose Simple, see documentation
        ds.setServerName(this.server); //VMware is the name set for the IP adress of the virtual machine on each of my systems
        ds.setPortNumber(5432);     //The port which PostgreSQL uses, see PGadmin3
        ds.setUser(this.user);   //User, which owns the database 'schokoladenfabrik'
        ds.setPassword(this.pw);   //Password of the user
        ds.setDatabaseName(this.dbname);    //Database, which shall be read
        con = null;    //connecting with the DataSource to its database
        con = ds.getConnection();
        con.setAutoCommit(false);   //deactivating autocommit, transactions are therefor enabled
        metaData = con.getMetaData();  //metadata for

        return con; //returning the Connection
    }

    /**
     * getter method for the MetaData of the current connection, which is set in the getContent method.
     * @return returns the current attribute of DatabaseMetaData.
     */
    public DatabaseMetaData getMetaData(){
        return this.metaData;
    }

    /**
     * reads out the content of every column of every table of the database
     * @param con the connection, which was before acquired through getCon
     * @return returns an ArrayList, which contains strings of the contents of all the colums of every table in the database
     * @throws SQLException relays the SQLException to the controller
     * @see <a href="https://github.com/dmelichar-tgm/Rueckwertssalto/blob/master/src/at/tgm/insy/backflip/prototype/Output_Prototype.java">Daniel Melichar's Exporter Prototype</a>
     */
    public ArrayList<String> getContent(Connection con) throws SQLException {
        this.con = con;
        DatabaseMetaData metaData = con.getMetaData();
        ResultSet rsTables = metaData.getTables(null, null, null, null);

        ArrayList<String> tables = new ArrayList<String>();
        ArrayList<String> columns = new ArrayList<String>();
        while (rsTables.next()) {
            tables.add(rsTables.getString(3));
        }

        ResultSet rsColumns = metaData.getColumns(null, null, rsTables.getString("TABLE_NAME"), "%");
        while (rsColumns.next()) {
            columns.add(rsColumns.getString("COLUMN_NAME"));
        }
        System.out.println(columns.toString());
        return columns;
    }
}
