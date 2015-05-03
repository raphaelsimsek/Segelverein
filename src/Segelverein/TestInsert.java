package Segelverein;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class to make it possible to test, whether or not the Connection is acutally working, without the, in this case,
 * unnecessary overhead of the data encapsulation.
 * @author Raphael Simsek 4CHITM
 * @version 2015-04-21
 */
/*
public class TestInsert {

    public static void main(String [] args){
        SegelModel model = null;
        SegelController cont = null;
        Connection conn = null;

        try {
            cont=new SegelController();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model=new SegelModel(cont);
        //Setting up Connection
        conn=model.getConn("VMware","segler","segler","segelverein");
        model.getDefaultTableModel(conn);
        try {
            conn.createStatement().executeUpdate("INSERT INTO boot VALUES (77,'Testschiff',10,95);");
            conn.commit();
            /*
            Committing right after the INSERT is essential, as the Change in the Table in my execution of the INSERT creates an immediate
             SELECT * from boot; to refresh the table

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
*/
