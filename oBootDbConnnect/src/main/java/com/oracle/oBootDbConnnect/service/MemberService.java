package com.oracle.oBootDbConnnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootDbConnnect.domain.Member7;
import com.oracle.oBootDbConnnect.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// 회원 가입
	public Long memberSave(Member7 member7) {
		System.out.println("MemberService memberSave start...");
		memberRepository.save(member7);
		return member7.getId();
	}

	// 전체 회원 조회
	public List<Member7> findMembers() {
		System.out.println("MemberService findMembers start...");
		return memberRepository.findAll();
	}

}
