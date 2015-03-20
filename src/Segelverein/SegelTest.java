package Segelverein;

import java.sql.SQLException;

/**
 * This is the test-class of the assignment 'Segelverein'
 * It calls the Controller and builds a connection with the specified credentials of the CLI, which it will use SegelLogin for.
 *
 * More content coming soon
 * @author Raphael Simsek 4CHITM
 * @version 2015-03-17
 */
public class SegelTest {

    /**
     * The main Method, which is able to run the application, as the all other methods are non-static.
     * @param args user input in the main method
     */
    public static void main(String[]args){
        SegelModel model=new SegelModel();
        SegelView view=new SegelView();

        try {
            new SegelController(model,view);  //call of the default constructor of the controller
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}