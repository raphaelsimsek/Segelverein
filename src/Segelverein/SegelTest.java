package Segelverein;

import javax.swing.*;
import java.sql.SQLException;

/**
 *
 * @author Raphael Simsek
 * @version 2015-03-22
 */
public class SegelTest {
    public static void main(String[] args) {
        try {
            new SegelController(args);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Please check your Console Errormessage","SQL Alert",JOptionPane.ERROR_MESSAGE);
        }
    }
}
