package com.oracle.studyJpa2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.studyJpa2.domain.Member;
import com.oracle.studyJpa2.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/new")
	public String createForm() {
		System.out.println("");
		return "members/createMemberForm";
	}
	@PostMapping(value="/memberSave")
	public String memberSave(Member member) {
		memberService.memberSave(member);
		return "redirect:/";
	}
}
