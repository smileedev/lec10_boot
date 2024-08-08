package com.gn.spring.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.spring.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	Page<Board> findByboardTitleContaining(String keyword, Pageable pageable);
	
	Page<Board> findByboardContentContaining(String keyword, Pageable pageable);
}
