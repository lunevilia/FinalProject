package com.project.finalProject.service;

import java.util.ArrayList;

import com.project.finalProject.model.TransactionVO;

public interface ITransactionService {

	void insertTransaction(TransactionVO vo);
	ArrayList<TransactionVO> listAllTran(); 
	TransactionVO selectTran(int ponstNo);
	void manageDeleteTran(int tranNo);	// 관리자 삭제
}