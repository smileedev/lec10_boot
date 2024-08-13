package com.gn.spring.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gn.spring.board.domain.Board;
import com.gn.spring.board.domain.BoardDto;
import com.gn.spring.board.repository.BoardRepository;
import com.gn.spring.member.domain.Member;
import com.gn.spring.member.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	
	
	public BoardDto selectBoardOne(Long board_no) {
		Board board = boardRepository.findByboardNo(board_no);
//		BoardDto dto = new BoardDto().toDto(board);
		BoardDto dto = BoardDto.builder()
					.board_no(board.getBoardNo())
					.board_title(board.getBoardTitle())
					.board_content(board.getBoardContent())
					.ori_thumbnail(board.getOriThumbnail())
					.new_thumbnail(board.getNewThumbnail())
					.reg_date(board.getRegDate())
					.mod_date(board.getModDate())
					.board_writer_no(board.getMember().getMemNo())
					.board_writer_name(board.getMember().getMemName())
					.build();
		return dto;
	}
	
	@Autowired
	public BoardService(BoardRepository boardRepository,
			MemberRepository memberRepository) {
		this.boardRepository = boardRepository;
		this.memberRepository = memberRepository;
	}
	
	@Transactional
	public Board updateBoard(BoardDto dto) {
		BoardDto temp = selectBoardOne(dto.getBoard_no());
		temp.setBoard_title(dto.getBoard_title());
		temp.setBoard_content(dto.getBoard_content());
		
		if(dto.getOri_thumbnail() != null && "".equals(dto.getOri_thumbnail()) == false) {
			temp.setOri_thumbnail(dto.getOri_thumbnail());
			temp.setNew_thumbnail(dto.getNew_thumbnail());
		}
		
		Board board = temp.toEntity();
		Board result = boardRepository.save(board);
		return result;
	}
	
	
	
	
	// 
	public Board createBoard(BoardDto dto) {
		Long boardWriter = dto.getBoard_writer_no();
		Member member = memberRepository.findBymemNo(boardWriter);
		
//		Board board = dto.toEntity();
		Board board = Board.builder()
				.boardTitle(dto.getBoard_title())
				.boardContent(dto.getBoard_content())
				.oriThumbnail(dto.getOri_thumbnail())
				.newThumbnail(dto.getNew_thumbnail())
				.member(member)
				.build();
		
		return boardRepository.save(board);
	}
	
	public Page<BoardDto> selectBoardList(BoardDto searchDto, Pageable pageable){
		Page<Board> boardList = null;
		
		
//		String boardTitle = searchDto.getBoard_title();
//		
//		if(boardTitle != null && "".equals(boardTitle)==false) {
//			boardList = boardRepository.findByboardTitleContaining(boardTitle, pageable);
//		}else {
//			boardList = boardRepository.findAll(pageable);
//		}
		
		String searchText = searchDto.getSearch_text();
		if(searchText != null && "".equals(searchText) == false) {
			int searchType = searchDto.getSearch_type();
			switch(searchType) {
			case 1:
				boardList = boardRepository.findByboardTitleContaining(searchText, pageable);
				break;
			case 2:
				boardList = boardRepository.findByboardContentContaining(searchText, pageable);
				break;
			case 3:
				boardList = boardRepository.findByboardTitleOrboardContentContaining(searchText, pageable);
				break;
			}
		}else {
			boardList = boardRepository.findAll(pageable);
		}
		
	
		
		List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
		for(Board b : boardList) {
			BoardDto dto = new BoardDto().toDto(b);
			boardDtoList.add(dto);
		}
		
		return new PageImpl<>(boardDtoList, pageable, boardList.getTotalElements());
		
//		List<Board> boardList = boardRepository.findAll();
//		
//		List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
//		
//		// Board(Entity)를 BoardDto로 바꿔주기
//		for(Board board : boardList) {
//			BoardDto boardDto = new BoardDto().toDto(board);
//			boardDtoList.add(boardDto);
//		}
//		
//		return boardDtoList;
	}
	
	public int deleteBoard(Long board_no) {
		int result = 0;
		// json은 리턴 타입 없음!
		try {
			boardRepository.deleteById(board_no);
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
