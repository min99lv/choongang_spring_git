package com.oracle.oBootHello.service;

import java.util.List;

import com.oracle.oBootHello.dto.Member1;
import com.oracle.oBootHello.repository.MemberRepository;
import com.oracle.oBootHello.repository.MemoryMemberRepository;

public class MemberService {
	// 전통적 방식 구현
	// 인터페이스 = new 실제 구현할 객체
	MemberRepository memberRepository = new MemoryMemberRepository();

	// 회원가입
	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSave start...");
		memberRepository.save(member1);
		return member1.getId();
	}

	public List<Member1> allMembers() {
		System.out.println("MemberService allMembers start...");
		List<Member1> memList = null;
		System.out.println("memList.size()->" + memList.size());
		return memList;
	}

}
