package Segelverein;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import java.sql.*;

import javax.swing.table.*;

/**
 * View Class of Segelverein, JavaDoc coming soon
 *
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-22
 */
public class SegelView {
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
    private DefaultComboBoxModel tableComboBoxModel;
    private DefaultComboBoxModel columnComboBoxModel;

    /**
     * Constructor for the View class, which contains the addtion of the Listeners for all of the GUI elements
     *
     * @param cont SegelController to apply all the Listeners to it
     */
    public SegelView(SegelController cont) {
        this.cont = cont;
        $$$setupUI$$$();
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
     *
     * @link: http://stackoverflow.com/questions/4577792/how-to-clear-jtable
     */
    public void repaint() {
        this.defaultTableModel = (DefaultTableModel) this.mainTable.getModel();
        this.defaultTableModel.getDataVector().removeAllElements();
        this.mainTable.setModel(this.cont.getModel());
        this.frame.repaint();
    }

    /**
     * Getter method for the defaultTableModel, which is used by the Controller to find the current JTables TableModel
     *
     * @return current JTable's defaultTableModel
     */
    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    /**
     * Setter for the defaultTableModel, to not need to regenerate the JTable after a change in the table.
     *
     * @param defaultTableModel The defaultTableModel, which will be set instead of the old TableModel
     */
    public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
        this.defaultTableModel = null;
        this.defaultTableModel = defaultTableModel;
        //regenerate, because without it the table will be filled with the same data twice
        this.mainTable = new JTable(this.defaultTableModel);
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

    public JComboBox<String> getTableComboBox() {
        return tableComboBox;
    }

    public void setTableComboBox(JComboBox<String> tableComboBox) {
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
        this.mainTable = new JTable(this.cont.getModel()); //sets the defaultTableModel as the tables contents
        this.mainTable.getModel().addTableModelListener(this.cont); //sets a Listener on the TableModel
        //because I set the columns for the correct datatypes for boot, it is possible to let it be sortable with the default row sorter.
        this.mainTable.setAutoCreateRowSorter(true);
        //System.out.println(tables[0]+tables[1]+tables[2]);

        //System.out.println(tableComboBox.getItemAt(0)+tableComboBox.getItemAt(1)+tableComboBox.getItemAt(2));

        this.tableComboBoxModel = new DefaultComboBoxModel<String>();
        this.columnComboBoxModel = new DefaultComboBoxModel<String>();
        this.tableComboBoxModel.addElement("Boot");
        this.tableComboBoxModel.addElement("Mannschaft");
        this.tableComboBoxModel.addElement("Wettschaft");
        this.tableComboBox = new JComboBox(this.tableComboBoxModel);
        //this.tableComboBox.setModel(this.tableComboBoxModel);

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setHorizontalTextPosition(4);
        label1.setText("SELECT Table");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        tableComboBox.setEditable(false);
        tableComboBox.setEnabled(true);
        tableComboBox.setMaximumRowCount(20);
        panel1.add(tableComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(mainTable);
        idCheckBox = new JCheckBox();
        idCheckBox.setEnabled(true);
        idCheckBox.setHorizontalAlignment(4);
        idCheckBox.setHorizontalTextPosition(10);
        idCheckBox.setText("INSERT id");
        panel1.add(idCheckBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        nameCheckBox = new JCheckBox();
        nameCheckBox.setEnabled(true);
        nameCheckBox.setHorizontalAlignment(4);
        nameCheckBox.setHorizontalTextPosition(10);
        nameCheckBox.setText("INSERT name");
        panel1.add(nameCheckBox, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        personenCheckBox = new JCheckBox();
        personenCheckBox.setEnabled(true);
        personenCheckBox.setHorizontalAlignment(4);
        personenCheckBox.setHorizontalTextPosition(10);
        personenCheckBox.setText("INSERT personen");
        panel1.add(personenCheckBox, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tiefgangCheckBox = new JCheckBox();
        tiefgangCheckBox.setEnabled(true);
        tiefgangCheckBox.setHorizontalAlignment(4);
        tiefgangCheckBox.setHorizontalTextPosition(10);
        tiefgangCheckBox.setText("INSERT tiefgang");
        panel1.add(tiefgangCheckBox, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        insertButton = new JButton();
        insertButton.setText("INSERT");
        panel1.add(insertButton, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalTextPosition(4);
        label2.setText("SELECT Column");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        columnComboBox = new JComboBox();
        panel1.add(columnComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(80, -1), null, 2, false));
        selectButton = new JButton();
        selectButton.setText("EXECUTE");
        panel1.add(selectButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rollbackButton = new JButton();
        rollbackButton.setText("ROLLBACK");
        panel1.add(rollbackButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("DELETE current row");
        panel1.add(deleteButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        commitButton = new JButton();
        commitButton.setText("COMMIT");
        panel1.add(commitButton, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
