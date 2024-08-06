package com.oracle.oBootBoardWarPom.dao;

import java.util.ArrayList;

import com.oracle.oBootBoardWarPom.dto.BDto;



public interface BDao {
	
	public ArrayList<BDto> boardList();
	public com.oracle.oBootBoardWarPom.dto.BDto contentView(String strId);
	//public BDto modify(String bId, String bName, String bTitle, String bContent);
	public void modify(com.oracle.oBootBoardWarPom.dto.BDto bdto);
	public void delete(String bId);
	public void write(String bName, String bTitle, String bContent);
	public com.oracle.oBootBoardWarPom.dto.BDto reply_view(int bId);
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
	
}
