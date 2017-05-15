package com.iwh.clod;

import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class UserInventories {

    private List<UserInventory> inventories;
    private Collectibles collectibleDatabase;

    public UserInventories(Collectibles collectibleDatabase){
        this.collectibleDatabase = collectibleDatabase;
        inventories = new ArrayList<>();
        loadInventories();
        BotLogger.info("User Inventories loaded.");
    }

    public void resetInventories(boolean reload){

        /*
        try {
            String stock = Util.getStringFromFile(Referendum.STOCKINVENTORY);
            Util.overwriteFile(Referendum.USERINVENTORIES, stock);
        }
        catch (FileNotFoundException e){
            BotLogger.fatal("File error in resetting inventories: " + e.getMessage());
            return;
        }

        BotLogger.info("Inventories set to stock...");

        */

        initFile();

        if (reload){
            reloadInventories();
        }
    }

    public void reloadInventories(){
        inventories.clear();
        loadInventories();
        BotLogger.info("Reloaded inventories.");
    }

    public void saveInventories(){
        JSONObject main = new JSONObject();

        JSONArray users = new JSONArray();

        for (UserInventory inventory : inventories){
            JSONObject userObj = new JSONObject();
            userObj.put("id", inventory.getID());
            userObj.put("points", inventory.getPoints());

            JSONArray collectibles = new JSONArray();

            for (Collectible c : inventory.getCollectibles()){
                collectibles.put(c.getId());
            }

            userObj.put("collection", collectibles);

            users.put(userObj);
        }

        main.put("inventories", users);

        BotLogger.info("Saving users!");
        Util.overwriteFile(Referendum.USERINVENTORIES, main.toString());
    }

    private void initFile(){
        BotLogger.info("Generating default userinventory file...");

        try {
            Referendum.USERINVENTORIES.createNewFile();
        }
        catch (IOException e){
            BotLogger.fatal("Error in initializing default inventory file: " + e.getMessage());
            return;
        }

        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        JSONObject defaultUser = new JSONObject();
        defaultUser.put("id", "-1");
        defaultUser.put("points", 0);
        defaultUser.put("collection", new JSONArray());

        array.put(defaultUser);

        object.put("inventories", array);

        Util.overwriteFile(Referendum.USERINVENTORIES, object.toString());

        BotLogger.info("Inventory file generated.");
    }

    public void loadInventories(){

        /*
        if (!Referendum.USERINVENTORIES.exists()){
            BotLogger.info("User-inventory file does not exist. Using stock...");
            try {
                Referendum.USERINVENTORIES.createNewFile();
            }
            catch (IOException e){
                BotLogger.info("Issue in creating the inventory file: " + e.getMessage());
            }
            resetInventories(true);
        }
        */

        if (!Referendum.USERINVENTORIES.exists()){
            initFile();
        }


        String invs = Util.getStringFromFileCompact(Referendum.USERINVENTORIES);

        if (invs.isEmpty()){
            BotLogger.warn("Problem loading user inventories!");
        }

        JSONArray array = new JSONObject(invs).getJSONArray("inventories");

        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);

            String invID = object.getString("id");

            int points = object.getInt("points");

            List<Collectible> collectibles = new ArrayList<>();
            JSONArray items = object.getJSONArray("collection");
            for (int j = 0; j < items.length(); j++) {
                String id = (String)items.get(j);
                Collectible item = collectibleDatabase.getCollectibleByID(id);
                if (item == null){
                    continue;
                }
                collectibles.add(item);
            }

            inventories.add(new UserInventory(invID, collectibles, points, collectibleDatabase));

        }
    }

    public boolean giveCollectible(User user, String id){
        String invID = user.getId();
        UserInventory inventory = findUserInventoryByID(invID);

        if (inventory == null){
            BotLogger.info("User " + user.getName() +" is not registered. Registering!");
            inventory = addUser(user);
        }

        if (inventory.hasCollectible(id)){
            return false;
        }

        return inventory.giveCollectible(id);
    }

    public UserInventory addUser(User user){
        UserInventory inventory = new UserInventory(user.getId(), collectibleDatabase);
        inventories.add(inventory);
        return inventory;
    }

    public UserInventory findUserInventoryByID(String ID){
        for (UserInventory inventory : inventories){
            if (inventory.getID().equals(ID)){
                return inventory;
            }
        }

        return null;
    }

    public String toString(){
        String str = "Inventories: ";
        for (UserInventory userInventory : inventories){
            str = str + "\n" + userInventory.toString();
        }

        return str;
    }

}
