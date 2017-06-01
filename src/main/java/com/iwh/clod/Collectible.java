package com.iwh.clod;

/**
 * A class representing a collectible item.
 *
 * Created by iWasHere on 5/11/2017.
 */
public class Collectible {

    private String id;
    private String name;
    private String emoji;
    private String description;
    private int price;

    public Collectible(String id, String name, String emoji, String desc, int price){
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.price = price;
        description = desc;
    }

    public String getName(){ return name; }
    public String getEmoji(){ return emoji; }
    public String getDescription(){ return description; }
    public String getId(){ return id; }
    public int getPrice(){ return price; }

    public String toString(){
        return getId() + " / " + getName() + " / " + getDescription() + " / Emoji: " + getEmoji();
    }

    /**
     * Gets the name of the collectible along with the emoji.
     *
     * @return The string representing formal name
     *
     * @author iWasHere
     */
    public String getFormalName(){
        return getEmoji() + " " + getName();
    }

    /**
     * Gets the info for the collectible as a string.
     *
     * @return The string representing info
     *
     * @author iWasHere
     */
    public String getFormalInfo(){
        return getEmoji() + " " + getName() + "\n*" + getDescription() + "*";
    }

}
