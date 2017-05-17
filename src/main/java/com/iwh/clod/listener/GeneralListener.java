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

import java.util.EventListener;

/**
 * Created by iWasHere on 5/12/2017.
 */
public class GeneralListener extends ListenerAdapter{

    public void onReady(ReadyEvent event){
        BotLogger.info("Startup successful.");
        event.
    }

    public void onDisconnect(DisconnectEvent event){
        BotLogger.warn("Connection lost!");
    }

    public void onReconnect(ReconnectedEvent event){
        BotLogger.info("Connection reestablished.");
    }

    public void onException(ExceptionEvent event){
        String cause = event.getCause().getMessage();
        BotLogger.warn("Exception: \n" + cause);
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContent();

        if (message.getAuthor().isBot()){
            return;
        }

        if (CommandHelper.isCommand("help", content)){
            channel.sendMessage(Util.getStringFromFileCompact(Referendum.HELPTEXT)).queue();
            return;
        }

        //Auto-Subreddit linking
        if (content.contains("/r/")){
            String subreddit = "";

            int start = content.indexOf("/r/") + 3;
            for (int i = start; i <= content.length(); i++){
                if (i == content.length() || content.charAt(i) == ' '){
                    subreddit = content.substring(start, i);
                    break;
                }
            }
            channel.sendMessage(":globe_with_meridians: https://www.reddit.com/r/" + subreddit).queue();
        }

        //Emoter
        if (CommandHelper.isCommand("emotify", content)){
            String[] args = CommandHelper.getArgs(content);

            if (args.length != 2){
                channel.sendMessage("Incorrect usage!").queue();
                return;
            }

            String emote = args[0];
            String string = args[1];

            channel.sendMessage(string.replaceAll(" ", emote)).queue();
        }

        //Blockify
        if (CommandHelper.isCommand("blockify", content)){
            String[] args = CommandHelper.getArgs(content);

            if (args.length != 1){
                channel.sendMessage("Incorrect usage!").queue();
                return;
            }

            String string = args[0].toLowerCase();
            String built = "";

            for (int i = 0; i < string.length(); i++){
                char ch = string.charAt(i);

                String emoji = Util.getEmojiForChar(ch);

                if (emoji.isEmpty()){
                    built = built + ch;
                    continue;
                }

                built = built + emoji;
            }

            channel.sendMessage(built).queue();

        }
    }

}
