package com.oracle.oBootBoardWarPom.command;

import java.util.ArrayList;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoardWarPom.dao.BDao;
import com.oracle.oBootBoardWarPom.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExcuteCommand {
	private final BDao jdbcDao;
	@Autowired
	public BExcuteCommand(BDao jdbcBDao) {
		this.jdbcDao = jdbcBDao;
	}
	public void bContentCmd(Model model) {
		System.out.println("BExcuteCommand bContentCmd start...");
		//Model을 MAP으로 전환하는 법
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bId = request.getParameter("bId");
		System.out.println("BExcuteCommand bContentCmd bId >> "+bId);
		BDto board = jdbcDao.contentView(bId);
		model.addAttribute("mvc_board", board);

	}

	
	public void bListCmd(Model model) {
		// DAO 연결
		ArrayList<BDto> boartDtoList = jdbcDao.boardList();
		System.out.println("BExcuteCommand boartDtoList.size() >> " + boartDtoList.size());
		model.addAttribute("boardList", boartDtoList);
	}
	
	
	public void ModifyCmd(Model model) {
		// 1. model Map선언
		// 2. parameter ->  bId, bName , bTitle , bContent
		// 3. jdbcDao.modify(bId, bName, bTitle, bContent);
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		// BDto로 감싸지 않고 보내는 법
		// jdbcDao.modify(bId, bName, bTitle, bContent);
		
		// BDto로 감싸서 보내는 법
		System.out.println("ModifyCmd bid, bname >> " + bId+","+ bName);
		BDto bdto =  new BDto(Integer.parseInt(bId), bName, bTitle, bContent);
		jdbcDao.modify(bdto);
		
	}
	
	public void bDeleteCmd(Model model) {
		//   1)  model이용 , map 선언
	    //	 2) request 이용 ->  bId 추출
	    //	 3) dao  instance 선언
	    //	 4) delete method 이용하여 삭제
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		
		jdbcDao.delete(bId);
		
	}
	
	public void bWriteCmd(Model model) {
//		  1) model이용 , map 선언
//		  2) request 이용 ->  bName  ,bTitle  , bContent  추출
//		  3) dao  instance 선언
//		  4) write method 이용하여 저장(bName, bTitle, bContent)
//         bid, bGroup,,bHit,  bStep, bIndent, bDate -> mvc_board_seq,mvc_board_seq, 0 , 0 , 0, sysdate
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
	}
	
	public void bReplyViewCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bid  추출
//		  3) dao  instance 선언
//		  4) reply_view method 이용하여 (bid)
//		    - BDto dto = dao.reply_view(bId);
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BDto dto = jdbcDao.reply_view(bId);
		
		model.addAttribute("reply_view", dto);
	}
	
	public void bReplyCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 -> bid,         bName ,  bTitle,
//		                    bContent ,  bGroup , bStep ,
//		                    bIndent 추출
//		  3) dao  instance 선언
//		  4) reply method 이용하여 댓글저장 
//		    - dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bId = Integer.parseInt(request.getParameter("bId"));
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
		
		jdbcDao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		
		
	}
	
}
