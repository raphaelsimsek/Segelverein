package Segelverein;

import com.sun.codemodel.internal.JOp;

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

/**
 * @link: http://docs.oracle.com/javase/tutorial/jdbc/basics/jdbcswing.html
 * The controller class of the assignment 'Segelverein'.
 * It is used to connect model and view, as well as to encapsulate the data from each other.
 *
 * In the future this class will check the ActionEvent e via the actionPerformed method, via which the controller will be
 *      connected to the view part of this application.
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelController implements ActionListener, FocusListener, TableModelListener, ItemListener, MouseListener{
    private SegelModel model;
    private SegelView view;
    private Connection currentCon=null;
    private DefaultTableModel defaultTableModel;
    private TableColumnModel tableColumnModel;
    private int firstRow;
    private int lastRow;
    private int index;
    private ItemEvent ie;
    private boolean insertID=false;
    private boolean insertName=false;
    private boolean insertPersonen=false;
    private boolean insertTiefgang=false;
    private int selectedRow=1;
    private Object updateSelected=null;
    private String hostname,username,password,database,port;

    /**
     * Constructor, which generates all the other objects and gives them its parameter
     * @throws SQLException to the main method
     */
    public SegelController(String hostname, String username, String password, String database, String port) throws SQLException{
        this.hostname=hostname;
        this.username=username;
        this.password=password;
        this.database=database;
        this.port=port;

        //Added view to the model, to fill the JCombobox wih tables of the db
        this.model=new SegelModel(this,null);
        this.view=new SegelView(this);
        this.view.start();

        //You only set the window visible, once all of the content is loaded to it
    }

    /**
     * Method to create the DefaultTableModel, to be used to create the JTable
     * @return defaultTableModel filled with columns and rows of the table boot for later use to generate the JTable
     */
    public DefaultTableModel getModel(){
        this.currentCon=this.model.getConn(this.hostname,this.username,this.password,this.database,this.port);
        this.defaultTableModel=this.model.getDefaultTableModel(this.currentCon);
        //this.tableColumnModel=this.model.getColumns(); NullPointerException on this Method, unsolved error!
        return this.defaultTableModel;

    }

    public TableColumnModel getTableColumnModel() {
        return tableColumnModel;
    }

    /**
     * TODO: Add functionality to delete and select buttons
     * TODO: Get rid of NullPointerException for the Delete button
     * @param ae ActionEvent used to read any hits of a button in the later coming GUI
     */
    public void actionPerformed(ActionEvent ae){
        /* DELETE Button Action Listener */
        if(ae.getSource()==view.getDeleteButton()){
            this.selectedRow=this.view.getMainTable().getSelectedRow();
            //this.selectedRow=-1;
            //System.out.println(this.selectedRow);
            Object deleteCell=this.view.getMainTable().getValueAt(this.selectedRow,0);
            System.out.println(deleteCell.toString());
            String query="DELETE FROM boot WHERE id="+deleteCell.toString();
            this.model.executeQuery(query);
            System.out.println("Row deleted: "+this.selectedRow);
            this.defaultTableModel.fireTableStructureChanged();

            try {
                this.currentCon.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.view.repaint();
        }

        /* COMMIT Button ActionListener */
        if(ae.getSource()==view.getCommitButton()){
            try {
                this.currentCon.commit();
                this.view.repaint();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error - Commit failed", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Commit successful", "Commit", JOptionPane.INFORMATION_MESSAGE);
        }
        if(ae.getSource()==view.getRollbackButton()){
            try {
                this.currentCon.rollback();
                this.view.repaint();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error - Rollback failed", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Rollback successful", "Rollback", JOptionPane.INFORMATION_MESSAGE);
        }

        /* INSERT Button ActionListener */
        if(ae.getSource()==view.getInsertButton()) {
            String id = "";
            String name = "";
            String personen = "";
            String tiefgang = "";
            if (!view.getIdCheckBox().isSelected()&&this.insertID) {
                JOptionPane.showMessageDialog(null, "The primary key has to be checked", "Error - Insert failed", JOptionPane.ERROR_MESSAGE);
            }else{
                id=JOptionPane.showInputDialog(null,"Please input a value for ID","ID value!",JOptionPane.QUESTION_MESSAGE);
                if(id.equals("")){
                    JOptionPane.showMessageDialog(null, "The primary key has to be inserted", "Error - Insert failed", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (view.getNameCheckBox().isSelected()&&this.insertName) {
                name="'"+JOptionPane.showInputDialog(null,"Please input a value for Name","Name value!",JOptionPane.QUESTION_MESSAGE)+"'";
                if(name.equals("")){
                    name="null";
                }
            }
            if (view.getPersonenCheckBox().isSelected()&&this.insertPersonen) {
                personen=JOptionPane.showInputDialog(null,"Please input a value for Personen","Personen value!",JOptionPane.QUESTION_MESSAGE);
                if(personen.equals("")){
                    personen="null";
                }
            }
            if (view.getTiefgangCheckBox().isSelected()&&this.insertTiefgang) {
                tiefgang=JOptionPane.showInputDialog(null,"Please input a value for Tiefgang","Tiefgang value!",JOptionPane.QUESTION_MESSAGE);
                if(tiefgang.equals("")){
                    tiefgang="null";
                }
            }

            /* EXECUTE SELECT Button ActionListener */
            if (view.getIdCheckBox().isSelected()) {
                String values = id + "," + name + "," + personen + "," + tiefgang;
                //System.out.println(values); Debugging
                this.model.insertInto("boot", values, this.currentCon);
                try {
                    currentCon.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.view.repaint();
            }
        }
        if(ae.getSource()==view.getSelectButton()){

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
     * @link www.java2s.com/Tutorial/Java/0240__Swing/ListeningforChangestotheRowsandColumnsofaJTableComponent.htm
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        this.firstRow = e.getFirstRow();
        this.lastRow = e.getLastRow();
        this.index = e.getColumn();

        switch (e.getType()) {
            case TableModelEvent.INSERT:
                for (int i = this.firstRow; i <= lastRow; i++) {
                    //System.out.println(i);
                }
                break;
            /**
             * TODO: Search for possibility to get set the TableModel directly to a ResultSet and then inside the database - RTFM
             */
            case TableModelEvent.UPDATE:
                if (this.firstRow == TableModelEvent.HEADER_ROW) {
                    if (this.index == TableModelEvent.ALL_COLUMNS) {
                        System.out.println("A column was added");
                    } else {
                        System.out.println(this.index + "in header changed");
                    }
                } else {
                    for (int i = this.firstRow; i <= this.lastRow; i++) {
                        if (this.index == TableModelEvent.ALL_COLUMNS) {
                            System.out.println("All columns have changed");
                        } else {
                            this.updateSelected=this.view.getMainTable().getValueAt(i,this.index);
                            System.out.println(this.updateSelected.toString() + ": Row:"+(i) + " Column:" + (this.index));
                        }
                    }
                }
                break;
            case TableModelEvent.DELETE:
                for (int i = this.firstRow; i <= this.lastRow; i++) {
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
                this.insertID=true;
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                this.insertID=false;
            }
        }
        if(ie.getSource()==view.getNameCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                this.insertName=true;
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                this.insertName=false;
            }
        }
        if(ie.getSource()==view.getPersonenCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                this.insertPersonen=true;
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                this.insertPersonen=false;
            }
        }
        if(ie.getSource()==view.getTiefgangCheckBox()){
            if(ie.getStateChange()==ItemEvent.SELECTED){
                this.insertTiefgang=true;
            }
            if(ie.getStateChange()==ItemEvent.DESELECTED){
                this.insertTiefgang=false;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse pressed on: Row: "+this.view.getMainTable().getSelectedRow() + " Column: "+this.view.getMainTable().getSelectedColumn());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
