package com.iwh.clod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Ref;
import java.util.Scanner;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class Util {

    public static void overwriteFile(File file, String text){
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            writer.write(text);

            writer.close();
        }
        catch (IOException e){
            BotLogger.fatal("IO problem: " + e.getMessage());
        }
    }

    public static String getStringFromFile(File file) throws FileNotFoundException{
        String s = "";

        try (Scanner scanner = new Scanner(file, "UTF-8")){
            while (scanner.hasNextLine()){
                s = s + scanner.nextLine();
            }
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException(file.getPath() + " does not exist.");
        }

        return s;
    }

    public static String getStringFromFileCompact(File file){
        try{
            return getStringFromFile(file);
        }
        catch (FileNotFoundException e){
            BotLogger.warn("File not found. " + e.getMessage());
        }

        return "";
    }

    public static String getEmojiForChar(char c){

        for (int num = 0; num <= 9; num++) {
            try {
                if (num == Integer.parseInt(String.valueOf(c))) {
                    return ":" + Referendum.Numbers.values()[num].getTranslation() + ":";
                }
            }
            catch (Exception e){

            }
        }

        for (char alph = 'a'; alph <= 'z'; alph++){
            if (alph == c){
                return ":regional_indicator_"+ c +":";
            }
        }

        return String.valueOf(c);
    }

}
