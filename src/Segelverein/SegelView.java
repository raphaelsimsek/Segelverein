package Segelverein;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import java.sql.*;

import javax.swing.table.*;

/**
 * View Class of Segelverein, JavaDoc coming soon
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-22
 */
public class SegelView{
    //generating attributes
    private SegelController cont;
    private JTable mainTable;
    private JPanel panel1;
    private JComboBox tableComboBox;
    private JButton deleteButton;
    private JButton commitButton;
    private JButton rollbackButton;
    private JButton insertButton;
    private JCheckBox tiefgangCheckBox;
    private JTextField tiefgangTextField;
    private JCheckBox personenCheckBox;
    private JTextField personenTextField;
    private JCheckBox nameCheckBox;
    private JTextField nameTextField;
    private JCheckBox idCheckBox;
    private JTextField idTextField;
    private JComboBox columnComboBox;
    private JButton selectButton;
    private JFrame frame;
    private DefaultTableModel defaultTableModel;
    private TableModel tableModel;

    /**
     * Constructor for the View class, which contains the addtion of the Listeners for all of the GUI elements
     * @param cont SegelController to apply all the Listeners to it
     */
    public SegelView(SegelController cont){
        this.cont=cont;
        this.deleteButton.addActionListener(this.cont);
        this.commitButton.addActionListener(this.cont);
        this.rollbackButton.addActionListener(this.cont);
        this.insertButton.addActionListener(this.cont);
        this.selectButton.addActionListener(this.cont);
        this.deleteButton.addActionListener(this.cont);
        this.mainTable.addFocusListener(this.cont);
        this.tiefgangCheckBox.addItemListener(this.cont);
        this.personenCheckBox.addItemListener(this.cont);
        this.nameCheckBox.addItemListener(this.cont);
        this.idCheckBox.addItemListener(this.cont);
        this.mainTable.addMouseListener(this.cont);
    }

    /**
     * Default constructor needed for the GUI builder of IntelliJ
     */
    public SegelView(){}

    /**
     * start method to set all of the GUIs and JFrames constants, as well as the size.
     */
    public void start() {
        frame = new JFrame("SegelView");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 800);
        frame.setResizable(true);
        frame.setTitle("Segelverein");
        mainTable.putClientProperty("terminateEditOnFocusLost", true);
        //sets the size of the JTable, before it gets scrolled with the JScrollpane
        this.mainTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
        this.mainTable.setFillsViewportHeight(true);
    }

    /**
     * Repaint method to make the JFrame repaint
     * @link: http://stackoverflow.com/questions/4577792/how-to-clear-jtable
     */
    public void repaint(){
        this.defaultTableModel=(DefaultTableModel)this.mainTable.getModel();
        this.defaultTableModel.getDataVector().removeAllElements();
        this.mainTable =new JTable(this.cont.getModel());
        this.frame.repaint();
    }

    /**
     * Getter method for the defaultTableModel, which is used by the Controller to find the current JTables TableModel
     * @return current JTable's defaultTableModel
     */
    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    /**
     * Setter for the defaultTableModel, to not need to regenerate the JTable after a change in the table.
     * @param defaultTableModel The defaultTableModel, which will be set instead of the old TableModel
     */
    public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
        this.defaultTableModel=null;
        this.defaultTableModel = defaultTableModel;
        //regenerate, because without it the table will be filled with the same data twice
        this.mainTable=new JTable(this.defaultTableModel);
    }

    /*
     Getter and Setter Methods for the attributes of the Class
      */
    public JTable getMainTable() {
        return mainTable;
    }

    public void setMainTable(JTable mainTable) {
        this.mainTable = mainTable;
    }

    public JComboBox getTableComboBox() {
        return tableComboBox;
    }

    public void setTableComboBox(JComboBox tableComboBox) {
        this.tableComboBox = tableComboBox;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getCommitButton() {
        return commitButton;
    }

    public void setCommitButton(JButton commitButton) {
        this.commitButton = commitButton;
    }

    public JButton getRollbackButton() {
        return rollbackButton;
    }

    public void setRollbackButton(JButton rollbackButton) {
        this.rollbackButton = rollbackButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public void setInsertButton(JButton insertButton) {
        this.insertButton = insertButton;
    }

    public JCheckBox getTiefgangCheckBox() {
        return tiefgangCheckBox;
    }

    public void setTiefgangCheckBox(JCheckBox tiefgangCheckBox) {
        this.tiefgangCheckBox = tiefgangCheckBox;
    }

    public JCheckBox getPersonenCheckBox() {
        return personenCheckBox;
    }

    public void setPersonenCheckBox(JCheckBox personenCheckBox) {
        this.personenCheckBox = personenCheckBox;
    }

    public JCheckBox getNameCheckBox() {
        return nameCheckBox;
    }

    public void setNameCheckBox(JCheckBox nameCheckBox) {
        this.nameCheckBox = nameCheckBox;
    }

    public JCheckBox getIdCheckBox() {
        return idCheckBox;
    }

    public void setIdCheckBox(JCheckBox idCheckBox) {
        this.idCheckBox = idCheckBox;
    }

    public JComboBox getColumnComboBox() {
        return columnComboBox;
    }

    public void setColumnComboBox(JComboBox columnComboBox) {
        this.columnComboBox = columnComboBox;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(JButton selectButton) {
        this.selectButton = selectButton;
    }

    /**
     * Custom create for the JTable, to create it with the defaultTableModel and make it sortable
     */
    private void createUIComponents() {
        this.mainTable=new JTable(this.cont.getModel()); //sets the defaultTableModel as the tables contents
        this.mainTable.getModel().addTableModelListener(this.cont); //sets a Listener on the TableModel
        //because I set the columns for the correct datatypes for boot, it is possible to let it be sortable with the default row sorter.
        this.mainTable.setAutoCreateRowSorter(true);
    }
}
