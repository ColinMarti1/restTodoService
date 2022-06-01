package com.axa.ch.its.resttodoservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/todos")
public class TodosRESTController {

    private TodosRepository todosRepository;

    @Autowired
    public TodosRESTController(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }


    @GetMapping("/greeting")
    public Greeting getGreeting() {
        return new Greeting("Hallo");
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(todosRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                    .body(todosRepository.findById(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
        public ResponseEntity<String> addTodo(@RequestBody Todo todo) {
            return todosRepository.save(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@RequestBody Todo todo, @PathVariable String id) {
        try {
            todo.setId(parseLong(id));
        } catch (NumberFormatException e) {
            return new ResponseEntity("Bitte geben sie eine kürzere ID ein und machen sie sicher das es nur Zahlen sind", HttpStatus.CONFLICT);
        }
        return todosRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        return new ResponseEntity<>(todosRepository.delteById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/{state}")
    public ResponseEntity<String> changeState(@PathVariable Long id, @PathVariable String state){
        if (state.equals("complete")||state.equals("incomplete")){
            return todosRepository.setCompletedWhereId(state.equals("complete"),id);
        }else {
            return new ResponseEntity<>("state not valid",HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/changeDate")
    public ResponseEntity<String> changeDate(@PathVariable Long id, @RequestBody Map<String, String> date){
        try {
            return todosRepository.setDateWhereId(LocalDateTime.parse(date.get("date")),id);
        } catch (DateTimeParseException e) {
           return new ResponseEntity<>("Date is not valid",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("No date found",HttpStatus.BAD_REQUEST);
        }
    }

}
