package Segelverein;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The controller class of the assignment 'Segelverein'.
 * It is used to connect model and view, as well as to encapsulate the data from each other.
 *
 * In the future this class will check the ActionEvent e via the actionPerformed method, via which the controller will be
 *      connected to the view part of this application.
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelController {
    private SegelModel model;
    private SegelView view;
    private SegelTest test;
    private Connection currentCon;

    public SegelController() throws SQLException{
        this.model = new SegelModel();
        this.currentCon=model.getCon("VMware","schoko","schoko","schokoladenfabrik");
        this.model.getContent(this.currentCon);
        this.view = new SegelView(this,model);
    }


    /**
     * Content coming soon
     * @param e Actionevent used to read any hits of a button in the later coming GUI
     */
    public void actionPerformed(ActionEvent e){

    }
}
