package com.example.demo.controller;

import com.example.demo.model.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByDueDateBefore(LocalDate dueDate, Sort sort);

    List<Todo> findByDueDate(LocalDate dueDate, Sort sort);

    List<Todo> findByDueDateAfter(LocalDate dueDate, Sort sort);

    List<Todo> findByReminderBetween(ZonedDateTime before,ZonedDateTime after);
}
