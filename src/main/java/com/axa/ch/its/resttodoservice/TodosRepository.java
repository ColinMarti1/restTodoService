package com.axa.ch.its.resttodoservice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class TodosRepository {
    private List<Todo> todos;

    public TodosRepository() {
        todos = new ArrayList<>();
        todos.add(new Todo(1, 2, "Noah schlagen", true, LocalDateTime.now()));
        todos.add(new Todo(2, 3, "Maulo slagen", false, LocalDateTime.now()));
    }

    public ResponseEntity save(Todo todo) {
        try {
            Todo checkTodo = findById(todo.getId());
            if (checkTodo != null) {
                try {
                    todos.set(todos.indexOf(checkTodo), todo);
                } catch (Exception e) {
                    return new ResponseEntity("Fehler beim Aktualisieren des Todos", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity("Todo erfolgreich Aktualisiert", HttpStatus.NO_CONTENT);
            }
        } catch (NoSuchElementException e) {

            try {
                todos.add(todo);
            } catch (Exception exception) {
                return new ResponseEntity("Fehler beim hinzufügen des Todos", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("Todo erfolgreich Hinzugefügt", HttpStatus.CREATED);
        }
        return new ResponseEntity("Maulo is gay", HttpStatus.CONFLICT);
    }

    public List<Todo> findAll() {
        return todos;
    }

    public Todo findById(long id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .get();
    }

    public String delteById(long id) {
        try {
            todos.remove(findById(id));
            return "Deleted";
        } catch (NoSuchElementException e) {
            return "Todo not Found";
        }
    }

    public ResponseEntity setCompletedWhereId(boolean state, Long id) {
        try {
            Todo todo = findById(id);
            todo.setCompleted(state);
            save(todo);
            return new ResponseEntity("Todo wurde Aktualisiert",HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Todo wurde nicht Gefunden",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity setDateWhereId(LocalDateTime date, Long id){
        try {
            Todo todo = findById(id);
            todo.setDeadLine(date);
            save(todo);
            return new ResponseEntity("Todo wurde Aktualisiert",HttpStatus.OK);
        } catch (NoSuchElementException e) {
        return new ResponseEntity("Todo wurde nicht Gefunden", HttpStatus.NOT_FOUND);
        }
    }
}
