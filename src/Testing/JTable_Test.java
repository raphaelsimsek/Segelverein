package Testing;

import javax.swing.*;
import java.awt.*;

/**
 * An application, which shall test the JTable, and its compatibility with a GUI
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-19
 */
public class JTable_Test extends JPanel{
    private JTable table;
    public JTable_Test(){

        String[] columnNames={"Name","Age","Gender"};

        Object[][] data={
                {"Bill","18","Male"},
                {"Mary","20","Female"},
                {"Rick","40","Male"}
        };

        table=new JTable(data,columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500,50));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    public static void main(String [] args){
        JFrame jf=new JFrame();
        JTable_Test gui=new JTable_Test();
        jf.setTitle("My first java table");
        jf.setSize(600,200);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(gui);
        jf.repaint();
    }
}
