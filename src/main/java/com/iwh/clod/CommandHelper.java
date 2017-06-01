package com.iwh.clod;

import java.util.ArrayList;
import java.util.List;

/**
 * Command utility class.
 *
 * Created by iWasHere on 5/12/2017.
 */
public class CommandHelper {

    /**
     * Checks if the source string is a command, using an array of command aliases.
     *
     * @param searches Aliases for the command
     * @param source The source string
     * @return Whether or not there is a match
     *
     * @author iWasHere
     */
    public static boolean isCommand(String[] searches, String source){
        for (String s : searches){
            if (isCommand(s, source)){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the source string is a command, matching the search term.
     *
     * @param search The command string to search for
     * @param source The source string
     * @return Whether or not there is a match
     *
     * @author iWasHere
     */
    public static boolean isCommand(String search, String source){
        search = Referendum.COMMANDPREFIX + search;

        if (source.length() < search.length()){ return false; }
        return source.substring(0, search.length()).equals(search);
    }

    /**
     * Gets the arguments for a command.
     * For example, a command such as
     * ';i info testitem'
     * has two arguments: info, testitem.
     *
     * This will also parse quotation marks.
     * ';emotify :fire: "Hello world!"'
     * Is still two arguments: :fire:, Hello world!
     *
     * @param command The source command string.
     * @return Array of strings for arguments
     *
     * @author iWasHere
     */
    public static String[] getArgs(String command){

        List<String> args = new ArrayList<>();

        for (int i = 0; i < command.length(); i++){
            //Search for the start of an arg
            if (command.charAt(i) == ' ' && i < command.length()){
                i++;

                boolean text = false;
                //Get spaced arguments. Gross but it had to happen.
                for (int j = i; j < command.length(); j++){

                    //Check if we're getting a string (an argument in quotes)
                    if (command.charAt(j) == '"'){
                        if (!text) {
                            text = true;
                            i++;
                        }else{
                            args.add(command.substring(i, j));
                            i = j-1;
                            break;
                        }
                    }

                    //Check for spaces to end arguments
                    if (!text) {
                        if (command.charAt(j) == ' ') {
                            args.add(command.substring(i, j));
                            i = j-1;
                            break;
                        } else if (j == command.length() - 1) {
                            args.add(command.substring(i));
                            i = j-1;
                            break;
                        }
                    }

                }
            }
        }

        return args.toArray(new String[0]);
    }

}
