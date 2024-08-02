package com.oracle.oBootHello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootHello.dto.Member1;
import com.oracle.oBootHello.repository.MemberRepository;
import com.oracle.oBootHello.repository.MemoryMemberRepository;


@Service
public class MemberService {
	// 1.전통적 방식 구현
	// 인터페이스 = new 실제 구현할 객체
	// MemberRepository memberRepository = new MemoryMemberRepository();

	// 2.DI 방식 구현 -> 생성자 DI방식으로 연동
	private final MemberRepository memberRepository; // 선언 -> 생성자에서 오토와이어
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	// 회원가입
	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSave start...");
		memberRepository.save(member1);
		return member1.getId();
	}

	public List<Member1> allMembers() {
		System.out.println("MemberService allMembers start...");
		List<Member1> memList = null;
	   	memList =  memberRepository.findAll();

		System.out.println("MemberService memList.size()->" + memList.size());
		for(Member1 member : memList) {
			System.out.println("MemberService member.getName()->"+member.getName());
		}
		return memList;
	}

}
