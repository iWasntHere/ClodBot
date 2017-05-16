package com.iwh.clod;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.omg.CORBA.portable.InputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class Collectibles {

    private List<Collectible> collectibles;

    public Collectibles(){
        loadCollectibles();
        BotLogger.info("Collectibles system initialized.");
    }

    public void loadCollectibles(){
        collectibles = new ArrayList<Collectible>();

        String items;

        try{
            items = Util.getStringFromFile(Referendum.COLLECTIBLES);
        }
        catch (FileNotFoundException e) {
            BotLogger.fatal("Collectibles file not found: " + e.getMessage());
            BotLogger.fatal("Therefore, no collectibles have been loaded.");

            return;
        }

        //Load item data through JSON file
        JSONArray array = new JSONObject(items).getJSONArray("items");
        for (int i = 0; i < array.length(); i++){
            String id, n, e, d;
            int p;
            JSONObject object = array.getJSONObject(i);

            id = object.getString("id");
            n = object.getString("name");
            e = object.getString("emoji");
            d = object.getString("desc");
            p = object.getInt("price");

            collectibles.add(new Collectible(id, n, e, d, p));
        }

        BotLogger.info(collectibles.size() + " collectibles loaded!");
    }

    public Collectible getRandomCollectible(){
        int index = (int) Math.floor(Math.random() * collectibles.size());
        return collectibles.get(index);
    }

    public Collectible getCollectibleByID(String id){
        for (Collectible c : collectibles){
            if (c.getId().equals(id)){
                return c;
            }
        }

        return null;
    }

    public Collectible getCollectibleByName(String name){
        name = name.toLowerCase();
        for (Collectible c : collectibles){
            if (c.getName().toLowerCase().equals(name)){
                return c;
            }
        }

        return null;
    }

    public String toString(){
        String string = "";
        for (Collectible c : collectibles){
            string = string + c.toString() + "\n";
        }

        return string;
    }

}
