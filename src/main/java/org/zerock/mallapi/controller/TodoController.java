package org.zerock.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.service.TodoService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    // 특정 tno의 todo 조회
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable Long tno) {
        return todoService.get(tno);
    }

    // list?page=1&size=10
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        return todoService.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO) {
        log.info("TodoDTO: " + todoDTO);
        Long tno = todoService.register(todoDTO);
        return Map.of("tno", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable Long tno,
                                      @RequestBody TodoDTO todoDTO) {
        todoDTO.setTno(tno);

        todoService.modify(todoDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable Long tno) {
        log.info("Remove:  " + tno);
        todoService.remove(tno);
        return Map.of("RESULT", "SUCCESS");
    }
}
