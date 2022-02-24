package com.project.finalProject.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.finalProject.dao.IChatDAO;
import com.project.finalProject.model.ChatMemberVO2;
import com.project.finalProject.model.ChatVO;
import com.project.finalProject.model.ComChatMemberVO2;
import com.project.finalProject.model.ComChatVO;

@Service
public class ChatService implements IChatService {

	@Autowired
	@Qualifier("IChatDAO")
	IChatDAO dao;
	
	
	@Override
	public ArrayList<ChatMemberVO2> listAllChat(int postNo) {
		// TODO Auto-generated method stub
		return dao.listAllChat(postNo);
	}


	@Override
	public void insertChat(ChatVO vo) {
		// TODO Auto-generated method stub
		dao.insertChat(vo);
	}


	@Override
	public ArrayList<ComChatMemberVO2> listAllChatCommunity(int comNo) {
		// TODO Auto-generated method stub
		return dao.listAllChatCommunity(comNo);
	}


	@Override
	public void insertChat2(ComChatVO vo) {
		// TODO Auto-generated method stub
		dao.insertChat2(vo);
	}


	@Override
	public ArrayList<ChatVO> listAllChatManager() {
		// TODO Auto-generated method stub
		return dao.listAllChatManager();
	}


	@Override
	public void deleteChat(int chatNo) {
		// TODO Auto-generated method stub
		dao.deleteChat(chatNo);
	}


	@Override
	public ChatMemberVO2 manageChatList(int chatNo) {
		// TODO Auto-generated method stub
		return dao.manageChatList(chatNo);
	}





	

	
}
