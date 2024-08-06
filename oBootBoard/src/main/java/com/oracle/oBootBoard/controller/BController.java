package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BController {
	private static final Logger logger = LoggerFactory.getLogger(BController.class);
	private final BExecuteCommand bExecuteService;

	@Autowired
	public BController(BExecuteCommand bExecuteService) {
		this.bExecuteService = bExecuteService;
	}

	// 게시글 목록
	@RequestMapping("list")
	public String list(Model model) {
		logger.info("list start...");
		bExecuteService.bListCmd(model); // call by reference boardDtoList
		model.addAttribute("count", "50");
		return "list";
	}

	// 게시글 보기
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view start...");

		model.addAttribute("request", request);
		bExecuteService.bContentCmd(model);

		return "content_view";
	}

	// 게시글 수정 --> post인데 값을 주지 않으면 옛날버전은 오류가 나므로 주의
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	// @RequestMapping(value = "/modify") --> 신규버전 사용 가능
	public String modify(HttpServletRequest request, Model model) {
		logger.info("modify start...");
		model.addAttribute("request", request);
		bExecuteService.bModifyCmd(model);

		return "redirect:list";
	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");

		model.addAttribute("request", request);
		bExecuteService.bDelteCmd(model);

		return "redirect:list";
	}

	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view start..");

		return "write_view";
	}

	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, Model model) {
		logger.info("write start...");

		model.addAttribute("request", request);
		bExecuteService.bWriteCmd(model);

		return "redirect:list";
	}
	// 댓글 작성
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view start...");

		model.addAttribute("request", request);
		bExecuteService.bReplyViewCmd(model);
		return "reply_view";
	}
	// 글을 작성하거나 수정할 때 -> post
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		
		model.addAttribute("request", request);
		bExecuteService.bReplyCmd(model);
		return "redirect:list";
	}
	
	

}
