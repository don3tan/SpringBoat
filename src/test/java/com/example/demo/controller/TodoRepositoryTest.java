package com.example.demo.controller;

import com.example.demo.model.Todo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    TodoRepository todoRepository;

    private Todo ndp2018;
    private Todo payBills;
    private Todo mc2018;
    private Todo mc2019;
    private Todo actuallySeeNdp2018;
    private Todo actuallySeeNdp2019;
    private Todo prepareWorkshop2018;
    private Todo neverDone;

    @Before
    public void setUp(){
        ndp2018 = save(LocalDate.of(2018,8,8),"NDP",false);
        payBills = save(LocalDate.of(2018,8,31),"Pay Bills", true);
        mc2018 = save(LocalDate.of(2018,8,10),"Take MC post NDP", false);
        mc2019 = save(LocalDate.of(2019,8,10),"Take MC post NDP", false);
        actuallySeeNdp2018 = save(LocalDate.of(2018,8,9),"NDP", false);
        actuallySeeNdp2019 = save(LocalDate.of(2019,8,9),"NDP", false);
        prepareWorkshop2018 = save(LocalDate.of(2018,8,14),"NDP", false);
        neverDone = save(LocalDate.of(2019,8,14),"NDP", false);


    }

    private Todo save(LocalDate dueDate, String text, boolean isDone) {
        return testEntityManager.persistAndFlush(new Todo(dueDate, text, isDone));
    }

    @Test
    public void findByDueDateBefore() {
        List<Todo> result = todoRepository.findByDueDateBefore(
                LocalDate.of(2018,8,17),
                Sort.by(Sort.Order.desc("dueDate")));
        assertThat(result).containsExactly(prepareWorkshop2018,mc2018,actuallySeeNdp2018,ndp2018);
    }

    @Test
    public void findByDueDateAfter() {
        List<Todo> result = todoRepository.findByDueDateAfter(
                LocalDate.of(2018,8,17),
                Sort.by(Sort.Order.desc("dueDate")));
        assertThat(result).containsExactly(neverDone,mc2019,actuallySeeNdp2019,payBills);
    }
}