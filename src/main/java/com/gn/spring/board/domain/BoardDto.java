package com.gn.spring.board.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDto {
	private Long board_no;
	private String board_title;
	private String board_content;
	private Long board_writer_no;
	private String board_writer_name;
	private LocalDateTime reg_date;
	private LocalDateTime mod_date;
	private String ori_thumbnail;
	private String new_thumbnail;
	
	private int search_type = 1;
	private String search_text;
	
	// DTO로 구성한 정보를 Entity로 변경
	// -> DB에 반영하기 위해서
	public Board toEntity() {
		// Entity의 매개변수 생성자 사용
		// -> 매개변수 생성자가 protected 되어 있기 때문에 builder 사용
		return Board.builder()
				.boardNo(board_no)
				.boardTitle(board_title)
				.boardContent(board_content)
				.oriThumbnail(ori_thumbnail)
				.newThumbnail(new_thumbnail)
				.regDate(reg_date)
				.modDate(mod_date)
				.build();
	}
	
	
	// Entity로 받아온(from DB) 정보를 사용할 때
	// Entity를 Dto로 변경
	public BoardDto toDto(Board board) {
		return BoardDto.builder()
				.board_no(board.getBoardNo())
				.board_title(board.getBoardTitle())
				.board_content(board.getBoardContent())
				.ori_thumbnail(board.getOriThumbnail())
				.new_thumbnail(board.getNewThumbnail())
				.reg_date(board.getRegDate())
				.mod_date(board.getModDate())
				.build();
	}
	
}
