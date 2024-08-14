package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberJpaController {
	private final MemberJpaService memberJpaService;

	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		System.out.println("MemberJpaController /memberJpa/new start.....");
		return "memberJpa/createMemberForm";
	}

	// 회원 가입
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		System.out.println("MemberJpaController /memberJpa/save");
		System.out.println("MemberJpaController create member::....." + member);
		memberJpaService.join(member);

		return "memberJpa/createMemberForm";
	}

	// 회원 목록 조회
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		System.out.println("MemberJpaController listMember start.....");
		List<Member> memberList = memberJpaService.getListAllMember();
		model.addAttribute("members", memberList);
		return "memberJpa/memberList";
	}

	// 회원 아이디로 존재 확인
	@GetMapping(value = "/memberJpa/memberUpdateForm")
	public String memberUpdateForm(Member member1, Model model) {
		Member member = null;
		String rtnJsp = "";
		System.out.println("MemberJpaController /memberJpa/memberUpdateForm member1.getId()::.... " + member1.getId());
		// null 값을 가져올 수 잇음
		Optional<Member> maybeMember = memberJpaService.findById(member1.getId());
		if (maybeMember.isPresent()) {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is not null...");
			member = maybeMember.get();
			model.addAttribute("member", member);
			model.addAttribute("message", "member가 존재하므로 수정해주세요");
//			rtnJsp = "forward:/members";
			rtnJsp = "memberJpa/memberModify";
		} else {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is null...");
			model.addAttribute("message", "member가 존재하지 않으니, 입력부터 수정해주세요");
			rtnJsp = "forward:/members";
		}

		return rtnJsp;
	}

	// 회원 이름 수정
	@GetMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		memberJpaService.memberUpdate(member);
		return "redirect:/members";
	}

}
