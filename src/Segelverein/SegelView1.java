package Segelverein;

import javax.swing.*;

/**
 * This is the view of the assignment 'Segelverein' by professor Borko and List.
 * It contains the GUI, using a JTable, to show the user the tables of the logged in database.
 * It also contains TabPanes for each of the tables.
 *
 * Content coming soon.
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelView1 extends JFrame{
    private JFrame jf;
    private JTable jt;
    private SegelController cont;
    private SegelModel model;
    /**
     * @param cont Object of controller, which will be used to get the shown data for the JTable.
     * @param model Object of the model,
     */
    public SegelView1(SegelController cont, SegelModel model){
        this.cont=cont;
        this.model=model;


    }
}
