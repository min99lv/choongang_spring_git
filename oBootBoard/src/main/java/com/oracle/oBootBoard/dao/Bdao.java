package com.oracle.oBootBoard.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard.dto.BDto;


public interface Bdao {
	public ArrayList<BDto> boardList();
	public BDto contentView(String strId);
	public BDto modify(String bId, String bName, String bTitle, String bContent);
	public void modify3(BDto bDto);
	public void delete(String bId);
	public BDto write(String bName, String bTitle, String bContent);
	public BDto reply_view(String bId);
	public void reply(String bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
}
