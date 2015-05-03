package Testing;

import org.apache.commons.cli.*;

import java.io.Console;
import java.util.InputMismatchException;

/**
 * @author Raphael Simsek
 * @version 2015-05-04
 */
public class Test_ApacheCLI {
    public static void main(String[] args) {
        String hostname,username,password,database;

        Options segelOptions=new Options();
        segelOptions.addOption("h","hostname",true,"Enter hostname, default: localhost");
        segelOptions.addOption("u","username",true,"Enter username, default: OS username");
        segelOptions.addOption("p","password",true,"Enter password, default: none");
        segelOptions.addOption("d","database",true,"Enter database name");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd=null;

        try {
            cmd=parser.parse(segelOptions,args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(cmd.hasOption("h")){
            hostname=cmd.getOptionValue("h");
        }else{
            hostname="localhost";
        }
        if(cmd.hasOption("u")){
            username=cmd.getOptionValue("u");
        }else{
            username=System.getProperty("user.name");
        }
        if(cmd.hasOption("p")){
            password=cmd.getOptionValue("p");
            if(password==null){
                Console cnsl=System.console();
                password=cnsl.readPassword("Password: ").toString();
            }
        }else{
            password="";
        }
        if(cmd.hasOption("d")){
            database=cmd.getOptionValue("d");
        }else{
            throw new InputMismatchException("The database-name is required to establish a connection");
        }


    }
}
