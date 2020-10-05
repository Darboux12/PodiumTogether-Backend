package com.podium.helper;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;

public class TestLogger {

    public static void setUp(){

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
        root.setAdditive(false);

    }

}
