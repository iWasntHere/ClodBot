package com.iwh.clod;

import com.iwh.clod.listener.CollectibleListener;
import com.iwh.clod.listener.GeneralListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Ref;
import java.util.Properties;

/**
 * Created by iWasHere on 5/11/2017.
 */
public class Bot {

    public static JDA bot;

    public static void main(String args[]) {

        Properties properties = new Properties();

        try{

            //Check for property file
            if (!Referendum.CONFIG.exists()){
                BotLogger.info("Config file does not exist. Creating...");
                Referendum.CONFIG_DIR.mkdirs();
                Referendum.CONFIG.createNewFile();

                //Init the property file with default values
                FileWriter writer = new FileWriter(Referendum.CONFIG);
                writer.write("bot_token=[your token here!]\n");
                writer.write("command_prefix=;\n");
                writer.write("enable_collectibles=true\n");
                writer.write("seconds_between_collectible_drops=60\n");
                writer.write("seconds_between_shop_restocks=60\n");
                writer.close();
                BotLogger.info("Config file created under " + Referendum.CONFIG.getAbsolutePath() + "\nPlease set the bot_token property in this file,\nthen relaunch!");
                System.exit(0);
            }

            FileInputStream stream = new FileInputStream(Referendum.CONFIG);
            properties.load(stream);
        }
        catch (IOException e){
            BotLogger.fatal("Error loading properties: " + e.getMessage());
        }

        SaveData.loadData();

        try {
            JDABuilder botBuilder = new JDABuilder(AccountType.BOT);
            Referendum.TIMEBETWEENITEMDROPS = Long.parseLong(properties.getProperty("seconds_between_collectible_drops"));
            Referendum.TIMEBETWEENSHOPRESTOCKS = Long.parseLong(properties.getProperty("seconds_between_shop_restocks"));
            Referendum.COMMANDPREFIX = properties.getProperty("command_prefix");
            Referendum.TOKEN = properties.getProperty("bot_token");

            botBuilder.setToken(Referendum.TOKEN);
            BotLogger.info("Using token "+Referendum.TOKEN);

            botBuilder.addEventListener(new GeneralListener());

            if (Boolean.parseBoolean(properties.getProperty("enable_collectibles"))) {
                botBuilder.addEventListener(new CollectibleListener());
                BotLogger.info("Collectibles is enabled!");
            }
            bot = botBuilder.buildAsync();
        }
        catch (Exception e){
            BotLogger.fatal("Fatal error during startup: \n" + e.getMessage());
        }

    }
}
