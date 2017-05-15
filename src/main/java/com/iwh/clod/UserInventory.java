package com.iwh.clod;

import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class UserInventory {

    private Collectibles collectibleDatabase;

    private String ownerID;

    private List<Collectible> collectibles;

    private int points;

    public UserInventory(String ownerID, List<Collectible> collectibles, int points, Collectibles collectibleDatabase){
        this.ownerID = ownerID;
        this.collectibles = collectibles;
        this.collectibleDatabase = collectibleDatabase;
        this.points = points;
    }

    public UserInventory(String ownerID, List<Collectible> collectibles, Collectibles collectibleDatabase){
        this(ownerID, collectibles, 0, collectibleDatabase);
    }

    public UserInventory(String ownerID, Collectibles collectibleDatabase){
        this(ownerID, new ArrayList<Collectible>(), collectibleDatabase);
    }

    public String getID(){
        return ownerID;
    }

    public int getPoints(){
        return points;
    }

    public void modifyPoints(int toMod){
        points += toMod;
    }

    public List<Collectible> getCollectibles(){
        return collectibles;
    }

    public boolean giveCollectible(String id){
        if (hasCollectible(id)){
            return false;
        }

        Collectible collectible = collectibleDatabase.getCollectibleByID(id);
        if (collectible == null){
            return false;
        }

        collectibles.add(collectible);
        return true;
    }

    public boolean hasCollectible(String id){
        for (Collectible c : collectibles){
            if (c.getId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public String toString(){
        String str = "Inventory for '" + ownerID + "': ";
        str = str + "\n" + points + " points";
        for (Collectible c : collectibles){
            str = str + "\n" + c.toString();
        }

        return str;
    }

}
