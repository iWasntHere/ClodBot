package com.iwh.clod;

import java.util.Date;

/**
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

    public boolean checkIfNeedsRestock(){

    }

    public void stock(){
        for (int i = 0; i < collectibles.length; i++){
            Collectible c = collectibleDatabase.getRandomCollectible();

            //Make sure each item is stocked once
            if (!isItemStocked(c)) {
                collectibles[i] = collectibleDatabase.getRandomCollectible();
            }else{
                i--;
            }
        }

        BotLogger.info("Shop has been stocked.");
    }

    public boolean isItemStocked(Collectible collectible){
        for (Collectible c : collectibles){
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

    public Collectible[] getStock(){
        return collectibles;
    }

}
