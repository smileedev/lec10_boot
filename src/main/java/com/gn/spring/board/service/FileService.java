package com.gn.spring.board.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gn.spring.board.domain.Board;
import com.gn.spring.board.repository.BoardRepository;

@Service
public class FileService {
	private String fileDir = "C:\\board\\upload\\";
	
	private final BoardRepository boardRepository;
	
	@Autowired
	public FileService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public ResponseEntity<Object> download(Long board_no){
		try {
			Board b = boardRepository.findByboardNo(board_no);
			
			String newFileName = b.getNewThumbnail();
			String oriFileName = URLEncoder.encode(b.getOriThumbnail(), "UTF-8");
			String downDir = fileDir + newFileName;
			
			Path filePath = Paths.get(downDir);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
			
			File file = new File(downDir);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(oriFileName).build());
			
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	public String upload(MultipartFile file) {
		
		String newFileName = null;
		
		try {
			// 1. 파일 원래 이름
			String oriFileName = file.getOriginalFilename();
			
			// 2. 파일 자르기
			String fileExt 
				= oriFileName.substring(oriFileName.lastIndexOf("."), oriFileName.length());
			
			// 3. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			
			// 4. 8자리마다 포함되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			
			// 5. 새로운 파일명으로 만들기
			newFileName = uniqueName + fileExt;
			
			// 6. 파일 저장 경로 설정(전역변수로 이동)
			
			// 7. 파일 껍데기 생성
			File saveFile = new File(fileDir + newFileName);
			
			// 8. 경로 존재 여부 확인
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			
			// 9. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}
}
