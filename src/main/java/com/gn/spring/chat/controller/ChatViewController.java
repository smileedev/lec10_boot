package com.gn.spring.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gn.spring.chat.domain.ChatRoomDto;
import com.gn.spring.chat.service.ChatService;


@Controller
public class ChatViewController {
	
private final ChatService chatService;
	
	@Autowired
	public ChatViewController(ChatService chatService) {
		this.chatService = chatService;
	}
	
	
	@GetMapping("/chat")
	public String selectChatRoom(Model model,
			@PageableDefault(page=0, size=10, sort="lastDate", direction=Sort.Direction.DESC) Pageable pageable) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String memId = user.getUsername();
		
		Page<ChatRoomDto> resultList = chatService.selectChatRoomList(pageable, memId);
		System.out.println("목록 전달 확인");
		System.out.println(resultList);
		model.addAttribute("resultList", resultList);
		
;		
		return "chat/list";
	}
}
