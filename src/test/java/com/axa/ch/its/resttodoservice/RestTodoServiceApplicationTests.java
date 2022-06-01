package com.axa.ch.its.resttodoservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RestTodoServiceApplicationTests {

    TodosRepository todosRepository;
    TodosRESTController todosRESTController;


    //Todos für Tests
    Todo todo = new Todo(1L, 2L, "Noah schlagen", true, LocalDateTime.now());
    Todo postTodo = new Todo(1L, 69L, ";)", true, LocalDateTime.now());
    Todo postFailTodo = new Todo(25345235, 343246256,"L",false, LocalDateTime.now());

    @BeforeEach
    public void Initialize() {
        todosRepository = new TodosRepository();
        todosRESTController = new TodosRESTController(todosRepository);
    }

    @Test
    public void GetOneByIdTestStatusCode() {
        assertEquals(HttpStatus.OK, todosRESTController.getTodoById(2L).getStatusCode());
    }

    @Test
    public void GetOneByIdTestBody() {
        assertEquals(todo, todosRESTController.getTodoById(2L).getBody());
    }

    @Test
    public void GetOneByIdFail(){
        assertEquals(HttpStatus.NOT_FOUND, todosRESTController.getTodoById(20L).getStatusCode());
    }

    @Test
    public void PostHappyFlow() {
        assertEquals(HttpStatus.CREATED, todosRESTController.addTodo(postTodo).getStatusCode());
    }

    @Test
    public void PostTodoAlreadyExist(){
        assertEquals(HttpStatus.NO_CONTENT, todosRESTController.addTodo(todo).getStatusCode());
    }

}
