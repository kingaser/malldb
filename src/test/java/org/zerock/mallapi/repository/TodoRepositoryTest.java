package org.zerock.mallapi.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.QTodo;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JPQLQueryFactory queryFactory;

    @Disabled
    @Test
    public void testInsert() {
        for (int i = 1; i <= 100; i++) {
            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .dueDate(LocalDate.of(2023, 12, 31))
                    .writer("user" + i)
                    .build();

            todoRepository.save(todo);
        }
    }

    @Test
    public void testRead() {
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }

    @Disabled
    @Test
    public void testUpdate() {
        // 먼저 로딩하고 엔티티 객체를 변경
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        todo.changeTitle("Update Title...33");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2023, 10, 10));

        todoRepository.save(todo);
    }

    @Test
    public void testDelete() {
        Long tno = 1L;
        todoRepository.deleteById(tno);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);
        log.info(result.getTotalElements());
        result.getContent().forEach(todo -> log.info(todo));
    }

    // title로 검색하는 페이징 처리
    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findByTitleContaining("1", pageable);
        log.info(result.getTotalElements());
    }

    // QTodo를 이용해서 title로 '11'이라는 글자가 있는 데이터 검색
    @Test
    public void testSearch2() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("tno").descending());

        // JPQLQueryFactory를 이용해서 검색
        QTodo qTodo = QTodo.todo;

        List<Todo> list = queryFactory.selectFrom(qTodo)
                .where(qTodo.title.contains("11")).fetch();

        log.info(list);
    }

    @Test
    public void testSearch3() {

        String keyword = "11";

        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(2)
                .build();

        // TodoRepository의 search
        PageResponseDTO<TodoDTO> responseDTO = todoRepository.search(keyword, requestDTO);

        log.info(responseDTO);

    }
}
