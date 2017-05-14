package com.iwh.clod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igota on 5/12/2017.
 */
public class CommandHelper {

    public static boolean isCommand(String[] searches, String source){
        for (String s : searches){
            if (isCommand(s, source)){
                return true;
            }
        }

        return false;
    }

    public static boolean isCommand(String search, String source){
        search = Referendum.COMMANDPREFIX + search;

        if (source.length() < search.length()){ return false; }
        return source.substring(0, search.length()).equals(search);
    }

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
