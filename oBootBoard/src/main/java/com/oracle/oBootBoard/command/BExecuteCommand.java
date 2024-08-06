package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootBoard.dao.Bdao;
import com.oracle.oBootBoard.dao.JdbcDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExecuteCommand {
	private final Bdao jdbcDao; // final 생성자로 인스턴스화 해주어야함

	@Autowired
	public BExecuteCommand(Bdao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public void bContentCmd(Model model) {
		System.out.println("BExecuteCommand bContentCmd start...");
		// model이를 Map으로 전환
		// "request" , request
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		BDto board = jdbcDao.contentView(bId);

		model.addAttribute("mvc_board", board);
	}

	public void bListCmd(Model model) {
		// DAO 연결
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BExecuteCommand boardDtoList.size()-->>" + boardDtoList.size());
		model.addAttribute("boardList", boardDtoList);
	}

	public void bModifyCmd(Model model) {
		// 1. model Map선언
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		// 2. parameter -> bId, bName , bTitle , bContent
//		String bId = request.getParameter("bId");
		int bId = Integer.parseInt(request.getParameter("bId"));
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		// 3. jdbcDao.modify(bId, bName, bTitle, bContent);
		// BDto bDto = jdbcDao.modify(bId, bName, bTitle, bContent);
		//
		System.out.println("bModifyCmd bId->" + bId);
		System.out.println("bModifyCmd bName->" + bName);
		System.out.println("bModifyCmd bTitle->" + bTitle);
		BDto bDto = new BDto(bId, bName, bTitle, bContent);
		jdbcDao.modify3(bDto);
	}

	public void bDelteCmd(Model model) {
		// 1) model이용 , map 선언
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		// 2) request 이용 -> bId 추출
		String bId = request.getParameter("bId");
		System.out.println("bDelteCmd bId->" + bId);

		// 3) dao instance 선언
		jdbcDao.delete(bId);
		// 4) delete method 이용하여 삭제

	}

	public void bWriteCmd(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		System.out.println("BExecuteCommand bWriteCmd " + bName);
		BDto bDto = new BDto();

		bDto = jdbcDao.write(bName, bTitle, bContent);
	}

	public void bReplyViewCmd(Model model) {
//		1)  model이용 , map 선언
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		  2) request 이용 ->  bid  추출
		String bId = request.getParameter("bId");
//		  3) dao  instance 선언
		BDto dto = jdbcDao.reply_view(bId);
//		  4) reply_view method 이용하여 (bid)
//		    - BDto dto = dao.reply_view(bId);
		
		model.addAttribute("reply_view", dto);
	}

	public void bReplyCmd(Model model) {
//		  1)  model이용 , map 선언
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		  2) request 이용 -> bid,         bName ,  bTitle,
//		                    bContent ,  bGroup , bStep ,
//		                    bIndent 추출
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
//		  3) dao  instance 선언
//		  4) reply method 이용하여 댓글저장 
//		    - dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		jdbcDao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	
		
		
	}
}
