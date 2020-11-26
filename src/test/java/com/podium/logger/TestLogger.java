package com.podium.logger;

import ch.qos.logback.classic.Level;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.slf4j.LoggerFactory;

public class TestLogger {

    public static void setUp(){

        RestAssured.defaultParser = Parser.JSON;
        ch.qos.logback.classic.Logger root
                = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
        root.setAdditive(false);


    }

}
