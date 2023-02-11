package com.favmovie.util;

public enum CustomClaims {
    USER_ID("id"),
    USER_NAME("username");
    private final String claim;

    CustomClaims(String claim) {
        this.claim = claim;
    }

    public String getValue() {
        return claim;
    }
}
