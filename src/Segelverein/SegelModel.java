package Segelverein;

import org.postgresql.ds.PGSimpleDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.*;
import java.sql.*;
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
    private SegelController cont;
    private String server;
    private String user;
    private String pw;
    private String dbname;
    private static Connection conn;
    private ResultSetMetaData rsmd;
    private Statement sqlState;
    private ResultSet rs;
    //private Boolean first=true; I haven't found the reason yet, but the getDefaultTableModel always used to get called twice, resulting in a unaccurate JTable of the database.
    private static Object[] columns={"id","name","personen","tiefgang"};
    private static Object[][] data;
    static TableColumnModel tableColumnModel;
    //overriding directly in the definition, see: http://www.newthinktank.com/2012/04/java-video-tutorial-36/
    static DefaultTableModel defaultTableModel = new DefaultTableModel(data, columns){
        public Class getColumnClass(int column) {
            Class returnValue;
            // Verifying that the column exists (index > 0 && index < number of columns
            if ((column >= 0) && (column < getColumnCount())) {
                returnValue = getValueAt(0, column).getClass();
            } else {
                // Returns the class for the item in the column
                returnValue = Object.class;
            }
            return returnValue;
        }
    };


    /**
     * main/default constructor - setting default values for attributes
     */
    public SegelModel(SegelController cont){
        this.cont=cont;
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
    public Connection getConn(String server, String user, String pw, String dbname){
        //setting the parameters to the local attributes
        this.server=server;
        this.user=user;
        this.pw=pw;
        this.dbname=dbname;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://"+this.server+"/"+this.dbname, this.user, this.pw);


        /*PGSimpleDataSource ds = new PGSimpleDataSource();     //PostgreSQL DataSource Class, chose Simple, see documentation
        ds.setServerName(this.server); //VMware is the name set for the IP adress of the virtual machine on each of my systems
        ds.setPortNumber(5432);     //The port which PostgreSQL uses, see PGadmin3
        ds.setUser(this.user);   //User, which owns the database 'schokoladenfabrik'
        ds.setPassword(this.pw);   //Password of the user
        ds.setDatabaseName(this.dbname);    //Database, which shall be read
        conn = null;    //connecting with the DataSource to its database
        conn = ds.getConnection();
        */
            conn.setAutoCommit(false);   //deactivating autocommit, transactions are therefor enabled
        }catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error - Database credentials failed to login", JOptionPane.ERROR_MESSAGE);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error - Database Connection failed", JOptionPane.ERROR_MESSAGE);
        }

        return conn; //returning the Connection
    }

    /**
     * Generates the DefaultTableModel for later use in the JTable
     * @param conn Connection used to generate the DefaultTableModel
     * @return DefaultTableModel for JTable
     */
    public DefaultTableModel getDefaultTableModel(Connection conn){
        SegelModel.conn =conn;
        //int count=0; Debugging
        try{
            this.sqlState=conn.createStatement();
            String query="SELECT * FROM boot;";
            this.rs=sqlState.executeQuery(query);
            Object[] tempRow;
            while(rs.next()){
                tempRow=new Object[]{rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)};
                defaultTableModel.addRow(tempRow);
                //count++; Debugging double call issue
            }
            //this.first=false; Debugging
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error - Query Execution or Storage failed", JOptionPane.ERROR_MESSAGE);
        }
        //System.out.print(count); Debugging
        return defaultTableModel;
    }

    /**
     * A method to execute an Insert Query on any table.
     * @param tablename the name of the table, which the query shall be executed upon e.g. "boot"
     * @param values the already finished list of values, which shall be inserted into the columns of the table e.g. "53,'Titanic',20,200"
     * @param conn the connection, which shall be used to execute the query - wanted to have as much data encapsulated as possible.
     */
    public void insertInto(String tablename, String values,Connection conn){
        SegelModel.conn =conn;
        try{
            this.sqlState=conn.createStatement();
            //building query
            String query="INSERT INTO "+tablename+" VALUES ("+values+");";
            //System.out.println(query); Debugging

            //an insert is always executed with executeUpdate, as this is the only one, which doesn't try to get a ResultSet out of the query
            sqlState.executeUpdate(query);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error - Insert Execution failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * getter method for the ResultSetMetaData of the current connection, which is set in the getContent method.
     * @return returns the current attribute of DatabaseMetaData.
     */
    public ResultSetMetaData getRSMD(){
        return this.rsmd;
    }

    public TableColumnModel getColumns() {
        try {
            rsmd = rs.getMetaData();
            int numofCol = rsmd.getColumnCount();
            for (int i = 0; i <= numofCol; i++) {
                TableColumn runtime=new TableColumn(i);
                runtime.setHeaderValue(rsmd.getColumnName(i+1));
                tableColumnModel.addColumn(runtime);
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error - Column Metadata failed", JOptionPane.ERROR_MESSAGE);
        }
        return tableColumnModel;
    }

    /** (no-JavaDoc)
     * deprecated - not used anymore
     * reads out the content of every column of every table of the database
     * @param conn the connection, which was before acquired through getConn
     * @return returns an ArrayList, which contains strings of the contents of all the columns of every table in the database
     * @throws SQLException relays the SQLException to the controller
     * @see <a href="https://github.com/dmelichar-tgm/Rueckwertssalto/blob/master/src/at/tgm/insy/backflip/prototype/Output_Prototype.java">Daniel Melichar's Exporter Prototype</a>
     *
    public ArrayList<String> getContent(Connection conn) throws SQLException {
        SegelModel.conn = conn;
        DatabaseMetaData metaData = conn.getMetaData();
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

    }*/

}
