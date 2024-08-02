package com.oracle.oBootHello.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootHello.dto.Member1;
import com.oracle.oBootHello.service.MemberService;



@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	// 1.전통적 방식
	// MemberService memberService = new MemberService();
	// 2.DI방식 --> 생성자 DI
	// final -> 메모리는 생성되지 않고 객체만 생성하겠다
	private final MemberService memberService;
	
	@Autowired // 자동으로 연결 (컨틀롤러 클래스와 생성자를 내부적으로 연결)
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	@GetMapping(value = "members/memberForm")
	public String memberForm() {
		System.out.println("MemberController /members/memberForm Start..");
		return "members/memberForm";
	}
	
	@PostMapping(value = "/members/save")
	// jsp --> request.getParameter였는데 spring에서는 dto에 묻어서 들어온다
	public String save(Member1 member1) {
		System.out.println("MemberController /members/save start..");
		System.out.println("MemberController /members/save member.getName()-->>" + member1.getName());
		Long id = memberService.memberSave(member1);
		System.out.println("MemberController /members/save id-->>" + id);

		return "redirect:/";// 같은 컨트롤러내에 메소드를 호출할 때 사용
		// 가장 기본으로 보낸다 static에 있는 index를 탄다 
	}
	
	@GetMapping(value="/members/memberList")
	public String memberList(Model model) {
		logger.info("memberList start..");
		List<Member1> memberLists = memberService.allMembers();
		logger.info("memberLists.size()-->>{}",memberLists.size());
		model.addAttribute("memberLists", memberLists);
		
		return "members/memberList";
	}

}
