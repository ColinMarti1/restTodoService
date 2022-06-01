package com.axa.ch.its.resttodoservice;

import lombok.Data;

@Data
public class Greeting {
    private String message;

    public Greeting(String message) {
        this.message = message;
    }
}
