package com.iwh.clod;

import java.io.File;
import java.util.Enumeration;

/**
 * Reference class.
 *
 * Created by iWasHere on 5/11/2017.
 */
public class Referendum {

    public static String COMMANDPREFIX;
    public static long TIMEBETWEENITEMDROPS;
    public static long TIMEBETWEENSHOPRESTOCKS;

    public static String TOKEN;
    public static final String LOGNAME = "Clod-Bot";
    public static final String ADDURL = "https://discordapp.com/oauth2/authorize?client_id=312371988322582529&scope=bot";

    public static final File CONFIG_DIR = new File("src\\main\\resources\\config");

    public static final File SAVEDATA = new File("src\\main\\resources\\save.json");
    public static final File HELPTEXT = new File("src\\main\\resources\\interface\\help.txt");
    public static final File CONFIG = new File(CONFIG_DIR.getPath() + "\\config.properties");

    public static final File COLLECTIBLES = new File("src\\main\\resources\\collectible\\items.json");
    public static final File STOCKINVENTORY = new File("src\\main\\resources\\collectible\\inventories-empty.json");
    public static final File USERINVENTORIES = new File("src\\main\\resources\\collectible\\inventories.json");

    public static final String[] SUBREDDIT_ALIASES = {"/r/", "r/"};

    public enum Numbers{
        ZERO("zero"),
        ONE("one"),
        TWO("two"),
        THREE("three"),
        FOUR("four"),
        FIVE("five"),
        SIX("six"),
        SEVEN("seven"),
        EIGHT("eight"),
        NINE("nine");

        private String translation;

        Numbers(String translation){
            this.translation = translation;
        }

        String getTranslation(){
            return translation;
        }
    }
}
