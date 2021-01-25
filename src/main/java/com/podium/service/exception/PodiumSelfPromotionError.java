package com.podium.service.exception;

public class PodiumSelfPromotionError extends Exception {

    public PodiumSelfPromotionError(String roleName) {
        super("You cannot grant " + roleName + " role to yourself!");
    }
}
