package Segelverein;

import javax.swing.*;

/**
 * New View class for Segelverein, it used IntelliJ's GUI Form, which made it easier for me to create the GUI.
 * @author Raphael Simsek
 * @version 2015-03-20
 */
public class SegelView extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable table;
    private JButton selectButton;
    private JTextField selectTextField;
    private JButton deleteButton;
    private JButton insertButton;

    public SegelView() {
        this.setTitle("Segelverein");
        //put Controller in parameters this.deleteButton.addActionListener();
        this.setContentPane(new SegelView().panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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
