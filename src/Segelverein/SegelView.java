package Segelverein;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import java.sql.*;

import javax.swing.table.*;

/**
 * TODO: JavaDoc
 * View Class of Segelverein, JavaDoc coming soon
 * @author Raphael Simsek
 * @version 2015-03-22
 */
public class SegelView{
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

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
        this.defaultTableModel=null;
        this.defaultTableModel = defaultTableModel;
    }

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
    }
    public SegelView(){}

    public void start(String[] args) {
        frame = new JFrame("SegelView");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200,800);
        frame.setResizable(true);
        frame.setTitle("Segelverein");
        mainTable.putClientProperty("terminateEditOnFocusLost", true);
        this.mainTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
        this.mainTable.setFillsViewportHeight(true);
    }

    public void repaint(){
        frame.repaint();
    }

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

    public JTextField getTiefgangTextField() {
        return tiefgangTextField;
    }

    public void setTiefgangTextField(JTextField tiefgangTextField) {
        this.tiefgangTextField = tiefgangTextField;
    }

    public JCheckBox getPersonenCheckBox() {
        return personenCheckBox;
    }

    public void setPersonenCheckBox(JCheckBox personenCheckBox) {
        this.personenCheckBox = personenCheckBox;
    }

    public JTextField getPersonenTextField() {
        return personenTextField;
    }

    public void setPersonenTextField(JTextField personenTextField) {
        this.personenTextField = personenTextField;
    }

    public JCheckBox getNameCheckBox() {
        return nameCheckBox;
    }

    public void setNameCheckBox(JCheckBox nameCheckBox) {
        this.nameCheckBox = nameCheckBox;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JCheckBox getIdCheckBox() {
        return idCheckBox;
    }

    public void setIdCheckBox(JCheckBox idCheckBox) {
        this.idCheckBox = idCheckBox;
    }

    public JTextField getIdTextField() {
        return idTextField;
    }

    public void setIdTextField(JTextField idTextField) {
        this.idTextField = idTextField;
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

    private void createUIComponents() {
        this.mainTable=new JTable(this.cont.getModel());
        this.mainTable.getModel().addTableModelListener(this.cont);
        this.mainTable.setAutoCreateRowSorter(true);
    }
}
