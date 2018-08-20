package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "todo", schema = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate dueDate;
    private String text;
    @Column(name = "done")
    private boolean isDone;

    private ZonedDateTime reminder;

    public Todo() {

    }

    public Todo(long id, LocalDate dueDate, String text, boolean isDone) {
        this.id = id;
        this.dueDate = dueDate;
        this.text = text;
        this.isDone = isDone;
    }

    @JsonCreator
    public Todo(@JsonProperty("dueDate") LocalDate dueDate,
                @JsonProperty("text") String text,
                @JsonProperty("isDone") boolean isDone) {
        this.dueDate = dueDate;
        this.text = text;
        this.isDone = isDone;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
