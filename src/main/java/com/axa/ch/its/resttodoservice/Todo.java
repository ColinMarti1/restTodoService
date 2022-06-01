package com.axa.ch.its.resttodoservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Todo {
    private long userId;
    private long id;
    private String title;
    private boolean completed;
    private LocalDateTime deadLine;

    public Todo() {

    }
}
