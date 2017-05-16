package com.iwh.clod.listener;

import com.iwh.clod.*;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.DisconnectEvent;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.sql.Ref;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class CollectibleListener extends ListenerAdapter {

    private Collectibles collectibles;
    private UserInventories inventories;
    private Shop shop;

    private final String[] collectibleAliases = {"collectibles", "items", "i"};

    public void onReady(ReadyEvent event){
        collectibles = new Collectibles();
        inventories = new UserInventories(collectibles);
        shop = new Shop(collectibles);

        BotLogger.info("Drop time is " + Referendum.TIMEBETWEENITEMDROPS + " seconds.");
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContent();

        if (message.getAuthor().isBot()){
            return;
        }

        if (SaveData.canDrop()) {
            tryGiveCollectible(message, 100);
        }
        shop.tryRestock();

        if (CommandHelper.isCommand(collectibleAliases, content)){
            String[] args = CommandHelper.getArgs(content);

            if (args.length == 1) {
                switch (args[0]) {
                    case "inventories":
                        channel.sendMessage(inventories.toString()).queue();
                        break;
                    case "give":
                        tryGiveCollectible(message, 0);
                        break;
                    case "save":
                        inventories.saveInventories();
                        break;
                    case "reset":
                        inventories.resetInventories(true);
                        break;
                    case "owned":
                        UserInventory inventory = inventories.findUserInventoryByID(message.getAuthor().getId());
                        if (inventory == null) {
                            channel.sendMessage("Issue.").queue();
                            break;
                        }
                        channel.sendMessage(inventory.toString()).queue();
                        break;
                    case "list":
                        channel.sendMessage(collectibles.toString()).queue();
                        break;
                    case "time":
                        long time = SaveData.getDropTime(TimeUnit.SECONDS);
                        String str = (!SaveData.canDrop()) ? "Time until drop: " + (time) + " sec." : "Drop available!";
                        channel.sendMessage(str).queue();
                        break;
                    case "shop":
                        channel.sendMessage("Shop restocks in " + shop.getRestockTime() + " sec!").queue();
                        channel.sendMessage(shop.toString()).queue();
                        break;
                }
            }

            if (args.length == 2){
                switch (args[0]){
                    case "info":
                        String itemName = args[1];
                        Collectible c = collectibles.getCollectibleByName(itemName);

                        if (c != null){
                            channel.sendMessage(c.getFormalInfo()).queue();
                        }else{
                            channel.sendMessage("That item doesn't exist!").queue();
                        }
                        break;
                }
            }
        }
    }

    private void tryGiveCollectible(Message message, int random){
        if (random != 0 && Math.ceil(Math.random() * random) != 0){
            return;
        }

        BotLogger.info("Trying to give " + message.getAuthor().toString() + " a collectible.");

        Collectible collectible = collectibles.getRandomCollectible();
        boolean success = inventories.giveCollectible(message.getAuthor(), collectible.getId());
        if (success) {
            String msg = message.getAuthor().getAsMention() + " found " + collectible.getEmoji() + " " + collectible.getName() + "!";
            message.getChannel().sendMessage(msg).queue();

            SaveData.setLastDrop();
        }
    }

}
