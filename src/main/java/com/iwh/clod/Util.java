package com.iwh.clod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Utility method class.
 *
 * Created by iWasHere on 5/11/2017.
 */
public class Util {

    /**
     * Overwrites a file with the specified text.
     *
     * @param file The file to overwrite
     * @param text The text to write with
     *
     * @author iWasHere
     */
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

    /**
     * Reads a file and returns it's contents as a string.
     *
     * @param file The file to read
     * @return The contents of the file
     * @throws FileNotFoundException In the event of a nonexistant file
     *
     * @author iWasHere
     */
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

    /**
     * Gets a string from a file, catches errors for you.
     *
     * @param file File to read from
     * @return The contents of the file as a string
     *
     * @author iWasHere
     */
    public static String getStringFromFileCompact(File file){
        try{
            return getStringFromFile(file);
        }
        catch (FileNotFoundException e){
            BotLogger.warn("File not found. " + e.getMessage());
        }

        return "";
    }

    /**
     * Gets an emoji for a character. Does alphanumerics only.
     *
     * @param c Character to convert
     * @return Emoji string
     *
     * @author iWasHere
     */
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

    /**
     * Returns the difference between two times.
     *
     * For example, to get the amount of time (in seconds) that has passed since a certain date, do:
     *
     * <pre>
     *     <code>
     *         Math.abs(Util.getTimeDifference(startingDate, new Date(), TimeUnit.SECONDS));
     *     </code>
     * </pre>
     *
     * @param date1 The starting date
     * @param date2 The ending date
     * @param unit The unit of measurement to use
     * @return The amount of time between the two dates
     *
     * @author iWasHere
     */
    public static long getTimeDifference(Date date1, Date date2, TimeUnit unit){
        long dif = date1.getTime() - date2.getTime();
        return unit.convert(dif, TimeUnit.MILLISECONDS);
    }

    /**
     * Splits up a string into a list of strings.
     *
     * @param source The source string
     * @param spaceCheck Number of characters to begin checking for spaces
     * @param limit Maximum number of characters to split at
     * @return List of strings
     *
     * @author iWasHere
     */
    public static List<String> splitUpString(String source, int spaceCheck, int limit){
        List<String> strings = new ArrayList<>();

        int curPos = 0;
        boolean looping = true;
        while (looping){
            int iteration = 0;
            for (int i = curPos; i < source.length(); i++){

                if (i == source.length()-1){ //Terminate loop, we're at the end of the string
                    strings.add(source.substring(curPos));
                    looping = false;
                    break;
                }

                if (iteration > spaceCheck){ //Check for spaces X iterations in
                    if (source.charAt(i) == ' '){
                        strings.add(source.substring(curPos, i));
                        curPos = i+1;
                        break;
                    }
                }

                if (iteration > limit){ //No spaces? Cap length at 50
                    strings.add(source.substring(curPos, i));
                    curPos = i;
                    break;
                }

                iteration++;
            }
        }

        return strings;
    }

}
