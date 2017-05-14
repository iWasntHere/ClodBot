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
import java.util.logging.Logger;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class CollectibleListener extends ListenerAdapter {

    private Collectibles collectibles;
    private UserInventories inventories;

    public void onReady(ReadyEvent event){
        collectibles = new Collectibles();
        inventories = new UserInventories(collectibles);
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContent();

        if (message.getAuthor().isBot()){
            return;
        }

        tryGiveCollectible(message, 10000);

        if (CommandHelper.isCommand("collectibles", content)){
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
                }
            }
        }
    }

    private void tryGiveCollectible(Message message, int random){
        if (random != 0 && Math.ceil(Math.random() * random) != 0){
            return;
        }

        Collectible collectible = collectibles.getRandomCollectible();
        boolean success = inventories.giveCollectible(message.getAuthor(), collectible.getId());
        if (success) {
            String msg = message.getAuthor().getAsMention() + " found " + collectible.getEmoji() + " " + collectible.getName() + "!";
            message.getChannel().sendMessage(msg).queue();
        }
    }

}
