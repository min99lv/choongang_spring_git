package com.oracle.oBootBoardWarPom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.oBootBoardWarPom.command.BExcuteCommand;

import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class BController {
	
	private static final Logger logger = LoggerFactory.getLogger(BController.class);
	
	private final BExcuteCommand bExcuteService;
	
	@Autowired
	public BController(BExcuteCommand bExcuteService){
		this.bExcuteService = bExcuteService;
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		logger.info("list start...");
		
		bExcuteService.bListCmd(model);
		
		model.addAttribute("count", "50");
		
		return "list";
	}
	
	@RequestMapping("/content_view")
	public String content_view (HttpServletRequest request, Model model) {
		System.out.println("content_view start...");
		
		model.addAttribute("request", request);
		bExcuteService.bContentCmd(model);
		
		return "content_view";
	}
	
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	public String modify (HttpServletRequest request, Model model) {
		logger.info("modify start...");
		model.addAttribute("request", request);
		bExcuteService.ModifyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete start...");
		
		model.addAttribute("request", request);
		bExcuteService.bDeleteCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view...");
		return "write_view";
	}
	
	@PostMapping(value = "/write")
	public String write (HttpServletRequest request, Model model) {
		logger.info("write start...");
		
		model.addAttribute("request", request);
		bExcuteService.bWriteCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view start...");
		
		model.addAttribute("request", request);
		bExcuteService.bReplyViewCmd(model);
		
		return "reply_view";
	}
	
	@RequestMapping(value = "/reply", method=RequestMethod.POST)
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		
		model.addAttribute("request", request);
		bExcuteService.bReplyCmd(model);
		
		return "redirect:list";
	}
	
	
	
}
