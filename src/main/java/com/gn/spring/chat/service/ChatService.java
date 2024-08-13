package com.gn.spring.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gn.spring.chat.domain.ChatRoom;
import com.gn.spring.chat.domain.ChatRoomDto;
import com.gn.spring.chat.repository.ChatRoomRepository;

@Service
public class ChatService {
	
	public final ChatRoomRepository chatRoomRepository;
	
	@Autowired
	public ChatService(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}
	
	
	public Page<ChatRoomDto> selectChatRoomList(Pageable pageable, String memId){
		Page<ChatRoom> chatRoomList = chatRoomRepository.findAllByfromIdAndtoId(memId,pageable);
	
		List<ChatRoomDto> chatRoomDtoList = new ArrayList<ChatRoomDto>();
		for(ChatRoom c : chatRoomList) {
			ChatRoomDto dto = new ChatRoomDto().toDto(c);
			chatRoomDtoList.add(dto);
		}
		return new PageImpl<>(chatRoomDtoList, pageable, chatRoomList.getTotalElements());
	
	}
}
