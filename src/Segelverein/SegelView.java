package Segelverein;

import javax.swing.*;
import java.awt.*;

/**
 * New View class for Segelverein, it used IntelliJ's GUI Form, which made it easier for me to create the GUI.
 * @author Raphael Simsek
 * @version 2015-03-20
 */
public class SegelView extends JFrame{
    private SegelController cont;
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable table;
    private JButton selectButton;
    private JTextField selectTextField;
    private JButton deleteButton;
    private JButton insertButton;
    private JButton rollbackButton;
    private JButton commitButton;

    public SegelView(SegelController cont) {
        this.cont=cont;
        this.setTitle("Segelverein");
        this.deleteButton.addActionListener(this.cont);
        this.setContentPane(new SegelView(this.cont).panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.pack();
        this.setVisible(true);
    }

    //Getter methods to change the table, get the textfields text and to use ActionEvent on the Buttons
    public JTable getTable(){
        return table;
    }
    public JTextField getSelectTextField(){
        return selectTextField;
    }
    public JButton getInsertButton() {
        return insertButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSelectButton() {
        return selectButton;
    }
}
