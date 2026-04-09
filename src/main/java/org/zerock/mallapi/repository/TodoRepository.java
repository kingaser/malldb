package org.zerock.mallapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

    // title로 검색하는 페이징 처리
    Page<Todo> findByTitleContaining(String title, Pageable pageable);

}
