package com.iwh.clod;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A Shop class allowing users to purchase Collectibles.
 *
 * Created by iWasHere on 5/14/2017.
 */
public class Shop {

    private Collectible[] collectibles = new Collectible[5];

    private Collectibles collectibleDatabase;

    private Date lastStock;

    public Shop(Collectibles collectibleDatabase){
        this.collectibleDatabase = collectibleDatabase;

        stock();
    }

    /**
     * Checks if the shop needs to restock, and does.
     *
     * @author iWasHere
     */
    public void tryRestock(){
        if (checkIfNeedsRestock()){
            stock();
        }
    }

    /**
     * Checks if the shop needs to restock.
     * @return Whether it needs to restock or not
     *
     * @author iWasHere
     */
    public boolean checkIfNeedsRestock(){
        return (getRestockTime() <= 0);
    }

    /**
     * Returns the time in long until the shop needs to restock.
     * @return The time until restock
     *
     * @author iWasHere
     */
    public long getRestockTime(){
        return Referendum.TIMEBETWEENSHOPRESTOCKS + Util.getTimeDifference(lastStock, new Date(), TimeUnit.SECONDS);
    }

    /**
     * Stocks the shop with unique items (no duplicates).
     *
     * @author iWasHere
     */
    public void stock(){
        for (int i = 0; i < collectibles.length; i++){
            Collectible c = collectibleDatabase.getRandomCollectible();

            //Make sure each item is stocked once
            if (!isItemStocked(c)) {
                collectibles[i] = c;
            }else{
                i--;
            }
        }

        BotLogger.info("Shop has been stocked.");
        lastStock = new Date();
    }

    /**
     * Checks if a given Collectible is already stocked in the shop.
     *
     * @param collectible The collectible to check
     * @return If the collectible is stocked or not
     *
     * @author iWasHere
     */
    public boolean isItemStocked(Collectible collectible){
        for (Collectible c : collectibles){
            if (c == null){ continue; }
            if (c.getId().equals(collectible.getId())){
                return true;
            }
        }

        return false;
    }

    public String toString(){
        String str = "Shop Inventory:";
        for (Collectible c : collectibles){
            str = str + "\n  " + c.getFormalName() + " / " + c.getPrice() + " points";
        }

        return str;
    }

    /**
     * Returns the array of collectibles within the shop at the moment.
     *
     * @return The array of stocked items.
     *
     * @author iWasHere
     */
    public Collectible[] getStock(){
        return collectibles;
    }

}
