package com.oracle.studyJpa2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.studyJpa2.domain.Member;
import com.oracle.studyJpa2.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// 회원 가입
	public Member memberSave(Member member) {
		List<Member> listMember = memberRepository.findAll();
		return member;
	}

	// 회원 조회
	public List<Member> getListAllMember() {
		List<Member> listMember = memberRepository.findAll();
		return listMember;
	}

	// 회원 검색
	public Member findByMember(Long memberId) {
		Member member1 = memberRepository.findByMember(memberId);
		System.out.println("MemberService findByMember member1....>>" + member1);
		return member1;
	}

	public void memberUpdate(Member member) {
		System.out.println("MemberService memberUpdate member>>>>>>>>" + member);
		memberRepository.updateByMember(member);
		System.out.println("MemberService memberUpdate memberRepository.updateByMember after....");
		return;
	}

}
