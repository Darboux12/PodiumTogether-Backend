package com.podium.service.exception;

import java.time.LocalDateTime;
import java.util.Date;

public class PodiumUserBannedException extends Exception {

    public PodiumUserBannedException(LocalDateTime bannedTo, String reason) {
        super("You are banned to " + bannedTo + " Reason: " + reason);
    }
}
