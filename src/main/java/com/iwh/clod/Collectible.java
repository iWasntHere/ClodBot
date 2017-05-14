package com.iwh.clod;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class Collectible {

    private String id;
    private String name;
    private String emoji;
    private String description;

    public Collectible(String id, String name, String emoji, String desc){
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        description = desc;
    }

    public String getName(){ return name; }
    public String getEmoji(){ return emoji; }
    public String getDescription(){ return description; }
    public String getId(){ return id; }

    public String toString(){
        return getId() + " / " + getName() + " / " + getDescription() + " / Emoji: " + getEmoji();
    }

}
