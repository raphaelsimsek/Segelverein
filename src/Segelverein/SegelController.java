package Segelverein;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

/**
 * The controller class of the assignment 'Segelverein'.
 * It is used to connect model and view, as well as to encapsulate the data from each other.
 *
 * In the future this class will check the ActionEvent e via the actionPerformed method, via which the controller will be
 *      connected to the view part of this application.
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelController implements ActionListener, FocusListener, TableModelListener, ItemListener{
    private SegelModel model;
    private SegelView view;
    private SegelTest test;
    private Connection currentCon;
    private DefaultTableModel defaultTableModel;
    private TableColumnModel tableColumnModel;
    private int firstRow;
    private int lastRow;
    private int index;
    private ItemEvent ie;
    private boolean notFirst=false;

    /**
     * Constructor, which generates all the other objects and gives them its parameter
     * @param args Parameter of the main method needed to start the GUI off of IntelliJ's GUI builder.
     * @throws SQLException to the main method
     */
    public SegelController(String[] args) throws SQLException{
        this.model=new SegelModel(this);
        this.view=new SegelView(this);
        this.view.start(args);

        //You only set the window visible, once all of the content is loaded to it
    }

    /**
     * Method to create the DefaultTableModel, to be used to create the JTable
     * @return defaultTableModel filled with columns and rows of the table boot for later use to generate the JTable
     */
    public DefaultTableModel getModel(){
        this.currentCon=this.model.getConn("VMware","segler","segler","segelverein");
        this.defaultTableModel=this.model.getDefaultTableModel(this.currentCon);
        //this.tableColumnModel=this.model.getColumns(); NullPointerException on this Method, unsolved error!
        if(notFirst){
            this.view.setDefaultTableModel(this.defaultTableModel);
            this.view.repaint();
        }
        this.notFirst=true;
        return this.defaultTableModel;
    }

    public TableColumnModel getTableColumnModel() {
        return tableColumnModel;
    }

    /**
     * TODO: Add functionality to delete and select buttons
     * @param ae ActionEvent used to read any hits of a button in the later coming GUI
     */
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==view.getDeleteButton()){

        }
        if(ae.getSource()==view.getCommitButton()){
            try {
                this.currentCon.commit();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error - Commit failed", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Commit successful", "Commit", JOptionPane.INFORMATION_MESSAGE);
        }
        if(ae.getSource()==view.getRollbackButton()){
            try {
                this.currentCon.rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error - Rollback failed", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Rollback successful", "Rollback", JOptionPane.INFORMATION_MESSAGE);
        }
        if(ae.getSource()==view.getInsertButton()) {
            String id = "";
            String name = "";
            String personen = "";
            String tiefgang = "";
            if (!view.getIdCheckBox().isSelected()) {
                JOptionPane.showMessageDialog(null, "The primary key has to be checked", "Error - Insert failed", JOptionPane.ERROR_MESSAGE);
            }
            if (view.getNameCheckBox().isSelected()) {
                name = "'"+view.getNameTextField().getText()+"'";
            } else {
                name = "null";
            }
            if (view.getPersonenCheckBox().isSelected()) {
                personen = view.getPersonenTextField().getText();
            } else {
                personen = "null";
            }
            if (view.getTiefgangCheckBox().isSelected()) {
                tiefgang = view.getTiefgangTextField().getText();
            } else {
                tiefgang = "null";
            }
            if (!view.getIdCheckBox().isSelected() && view.getNameCheckBox().isSelected() && view.getPersonenCheckBox().isSelected() && view.getTiefgangCheckBox().isSelected()) {
                JOptionPane.showMessageDialog(null, "Please check at least the primary key: id", "Error - Insert failed", JOptionPane.ERROR_MESSAGE);
            }
            if (view.getIdCheckBox().isSelected()) {
                id=view.getIdTextField().getText();
                String values = id + "," + name + "," + personen + "," + tiefgang;
                //System.out.println(values); Debugging
                this.model.insertInto("boot", values, this.currentCon);
                this.getModel();
            }
        }
        if(ae.getSource()==view.getSelectButton()){

        }
        if(ae.getSource()==view.getDeleteButton()){

        }
    }

    /**
     * TODO: Add functionality to delete a focused row with JButton "DELETE (current row)"
     * Overridden FocusListener methods, shall be used to be able to delete a focused row off of the database.
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    /**
     * TODO: Make Update out of User's change to table.
     * Overridden tableChanged method, to catch any changes made to the table by the user, and then make an Update to the database out of it.
     * @param e This TableModelEvent is the one used to create the JTable - it reports any changes to the TableModelListener
     * see www.java2s.com/Tutorial/Java/0240__Swing/ListeningforChangestotheRowsandColumnsofaJTableComponent.htm
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        this.firstRow=e.getFirstRow();
        this.lastRow=e.getLastRow();
        this.index=e.getColumn();

        switch(e.getType()){
            case TableModelEvent.INSERT:
                for (int i=this.firstRow;i<=lastRow; i++){
                    System.out.println(i);
                }
                break;
            case TableModelEvent.UPDATE:
                if(this.firstRow == TableModelEvent.HEADER_ROW){
                    if(this.index == TableModelEvent.ALL_COLUMNS){
                        System.out.println("A column was added");
                    } else{
                        System.out.println(this.index+"in header changed");
                    }
                }else{
                    for (int i=this.firstRow; i<=this.lastRow; i++){
                        if(this.index == TableModelEvent.ALL_COLUMNS){
                            System.out.println("All columns have changed");
                        }else{

                            System.out.println((i+1)+" "+(this.index+1));
                        }
                    }
                }
                break;
            case TableModelEvent.DELETE:
                for(int i=this.firstRow; i<=this.lastRow; i++){
                    System.out.println(i);
                }
                break;
        }
    }

    /**
     * Overridden ItemListener Method for the JCheckbox'es in the GUI. It enables the JTextfield's for Inserts only once the Checkbox was selected.
     * @param ie ItemEvent used to get any change on the JCheckbox reported to the method,
     *           also used by actionPerformed method to check which Checkboxes are enabled on pressing the insert JButton.
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        this.ie=ie; //ItemEvent as an attribute for actionPerformed to use
        if(ie.getSource()==view.getIdCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                view.getIdTextField().setEnabled(true);
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                view.getIdTextField().setEnabled(false);
            }
        }
        if(ie.getSource()==view.getNameCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                view.getNameTextField().setEnabled(true);
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                view.getNameTextField().setEnabled(false);
            }
        }
        if(ie.getSource()==view.getPersonenCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                view.getPersonenTextField().setEnabled(true);
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                view.getPersonenTextField().setEnabled(false);
            }
        }
        if(ie.getSource()==view.getTiefgangCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                view.getTiefgangTextField().setEnabled(true);
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                view.getTiefgangTextField().setEnabled(false);
            }
        }
    }
}
