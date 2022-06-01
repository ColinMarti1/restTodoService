package com.axa.ch.its.resttodoservice;
import com.axa.ch.its.resttodoservice.TodosRESTController;
import com.axa.ch.its.resttodoservice.Todo;
import com.axa.ch.its.resttodoservice.TodosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
@SpringBootTest
class TodooApiApplicationTests {
    Todo toto = new Todo(2, 21, "quis ut nam facilis et officia qui", false, LocalDateTime.now());
    @Test
    public void test() {
        TodosRepository todosRepository = new TodosRepository();
        TodosRESTController todosRESTController = new TodosRESTController(todosRepository);
        assertEquals(HttpStatus.NO_CONTENT,todosRESTController.updateTodo(toto, String.valueOf(toto.getId())).getStatusCode());
    }
    @Test
    public void test1() {
        TodosRepository todosRepository = new TodosRepository();
        TodosRESTController todosRESTController = new TodosRESTController(todosRepository);
        assertEquals(HttpStatus.CREATED,todosRESTController.updateTodo(toto, String.valueOf(toto.getId())).getStatusCode());
    }
    @Test
    public void test2() {
        TodosRepository todosRepository = new TodosRepository();
        TodosRESTController todosRESTController = new TodosRESTController(todosRepository);
        assertEquals(HttpStatus.NOT_FOUND,todosRESTController.updateTodo(toto, String.valueOf(toto.getId())).getStatusCode());
    }
}