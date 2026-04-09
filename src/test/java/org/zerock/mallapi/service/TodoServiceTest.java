package org.zerock.mallapi.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Disabled
    @Test
    public void testRegister() {
        TodoDTO todoDTO = TodoDTO.builder()
                .title("Test Todo")
                .dueDate(LocalDate.of(2026, 04, 9))
                .writer("tester")
                .build();

        Long tno = todoService.register(todoDTO);

        log.info("TNO: " + tno);
    }

    @Test
    public void testRead() {
        Long tno = 1L;
        TodoDTO todoDTO = todoService.get(tno);
        log.info(todoDTO);
    }

    // modify에 대한 테스트 코드
    @Disabled
    @Test
    public void testModify() {
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(1L)
                .title("Test Update Title")
                .complete(true)
                .dueDate(LocalDate.of(2026, 04, 9))
                .build();
        todoService.modify(todoDTO);
    }

    // list() 에 대한 테스트 코드
    @Test
    public void testList() {
        log.info("--------------");

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(11)
                .build();

        PageResponseDTO<TodoDTO> responseDTO = todoService.list(pageRequestDTO);

        log.info(responseDTO);

        log.info(responseDTO.getPageNumList());
    }

}