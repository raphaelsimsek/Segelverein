package Segelverein;

import javax.swing.*;
import java.sql.SQLException;

/**
 * TODO: DELETE, SELECT functionalities, Apache CLI for multiple users, dynamic use of tables (esp. in the columns - see Controller)
 * Main method, used to generate the Controller
 * @author Raphael Simsek
 * @version 2015-03-22
 */
public class SegelTest {
    public static void main(String[] args) {
        try {
            new SegelController(args);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"SQL Alert",JOptionPane.ERROR_MESSAGE);
        }
    }
}
