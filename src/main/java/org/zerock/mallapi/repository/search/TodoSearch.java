package org.zerock.mallapi.repository.search;

import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.TodoDTO;

public interface TodoSearch {

    PageResponseDTO<TodoDTO> search(String keyword, PageRequestDTO pageRequestDTO);

}
