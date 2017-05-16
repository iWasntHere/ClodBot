package com.iwh.clod;

import org.json.JSONObject;

import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by iWasHere on 5/14/2017.
 */
public class SaveData {

    public static JSONObject savedata;

    private static Date lastCollectibleDrop;
    private static Date lastShopRestock;

    public static void loadData(){

        if (Referendum.SAVEDATA.exists()) {

            try {
                savedata = new JSONObject(Util.getStringFromFile(Referendum.SAVEDATA));

                long date = savedata.getLong("last_drop");
                lastCollectibleDrop = new Date(date);

                date = savedata.getLong("last_restock");
                lastShopRestock= new Date(date);
            } catch (IOException e) {
                BotLogger.fatal("Could not load data: " + e.getMessage());
            }

        }else{
            BotLogger.info("There is no save file! Making one...");
            initFile();
        }

        BotLogger.info("Finished loading...");
    }

    private static void initFile(){
        savedata = new JSONObject();

        savedata.put("last_drop", 0);
        savedata.put("last_restock", 0);

        Util.overwriteFile(Referendum.SAVEDATA, savedata.toString());
    }

    public static void saveData(){
        savedata.put("last_drop", lastCollectibleDrop.toString());
        savedata.put("last_restock", lastShopRestock.toString());

        Util.overwriteFile(Referendum.SAVEDATA, savedata.toString());
        BotLogger.info("Finished saving...");
    }

    public static void setLastDrop(){
        lastCollectibleDrop = new Date();
    }
    public static boolean canDrop(){
        return (getDropTime(TimeUnit.SECONDS) <= 0);
    }

    public static long getDropTime(TimeUnit unit){
        return Referendum.TIMEBETWEENITEMDROPS + Util.getTimeDifference(lastCollectibleDrop, new Date(), unit);
    }

}
