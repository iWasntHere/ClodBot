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

    /**
     * Modifies this userinventory's points by the given amount.
     *
     * @param toMod The amount of points to add
     *
     * @author iWasHere
     */
    public void modifyPoints(int toMod){
        points += toMod;
    }

    /**
     * Gets the list of owned collectibles.
     *
     * @return List of collectibles owned
     *
     * @author iWasHere
     */
    public List<Collectible> getCollectibles(){
        return collectibles;
    }

    /**
     * Tries to give a collectible to the user.
     * Returns whether or not this was successful. If the user has the collectible, or the collectible doesn't exist,
     * returns false.
     *
     * @param id The id of the collectible to give
     * @return Success
     *
     * @author iWasHere
     */
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

    /**
     * Returns whether or not this UserInventory has a given collectible.
     *
     * @param id The collectible to check for by id
     * @return If the user has the collectible
     *
     * @author iWasHere
     */
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
