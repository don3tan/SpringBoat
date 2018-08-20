package com.example.demo.controller;

import com.example.demo.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedStoredProcedureQueries;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@RepositoryRestController
@RequestMapping("/api")
public class TodoController {
    private static final Sort SORT_FOR_PAST = Sort.by(asc("isDone"), desc("dueDate"));
    private static final Sort SORT_FOR_PRESENT = Sort.by(asc("isDone"));

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos/display/{date}/past")
    @ResponseBody
    public List<Todo> getByPast(@PathVariable("date")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return todoRepository.findByDueDateBefore(date, SORT_FOR_PAST);
    }

    @GetMapping("/todos/display/{date}/present")
    @ResponseBody
    public List<Todo> getByPresent(@PathVariable("date")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return todoRepository.findByDueDate(date, SORT_FOR_PRESENT);
    }

    @PostMapping(path="/todos/item",
            consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Todo create(@RequestBody Todo item){
        return todoRepository.save(item);
    }

    @PutMapping(path = "/todos/item/{id}" ,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Todo update(@PathVariable long id, @RequestBody Todo update){
        return todoRepository.findById(id)
                .map(existing->{
                    existing.setText(update.getText());
                    existing.setDueDate(update.getDueDate());
                    existing.setDone(update.isDone());
                    return todoRepository.save(existing);
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/todos/reminders")
    @ResponseBody
    public List<Todo> getRemindersSecondsBefore(@RequestParam("at") ZonedDateTime at,
                                                @RequestParam("Seconds") int secondsBefore){
        return todoRepository.findByReminderBetween(at.minusSeconds(secondsBefore),at);
    }
}
