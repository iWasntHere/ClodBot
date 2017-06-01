package com.iwh.clod;

import net.dv8tion.jda.core.utils.SimpleLog;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Easy logging!
 *
 * Created by iWasHere on 5/11/2017.
 */
public class BotLogger {

    private static SimpleLog log = SimpleLog.getLog(Referendum.LOGNAME);

    public static void debug(Object message){
        log.debug(message);
    }

    public static void warn(Object message){
        log.warn(message);
    }

    public static void info(Object message){
        log.info(message);
    }

    public static void fatal(Object message){
        log.fatal(message);
    }

}
