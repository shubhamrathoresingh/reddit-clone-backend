package com.redditclone.webapp.exception;

public class SubRedditNotFoundException extends RuntimeException {

    public SubRedditNotFoundException(String message) {
        super(message);
    }
}