package com.oracle.oBootMybatis01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.repository.MemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberJpaService {
	private final MemberJpaRepository memberJpaRepository;

	// 회원 가입
	public void join(Member member) {
		System.out.println("MemberJpaService join member" + member);
		memberJpaRepository.save(member);
	}

	// 회원 조회
	public List<Member> getListAllMember() {
		List<Member> memberList = memberJpaRepository.findAll();
		System.out.println("MemberJpaService getListAllMember memberList.size();" + memberList.size());
		return memberList;
	}

	// 회원 아이디로 존재 확인
	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaService findById start....");
		Optional<Member> member = memberJpaRepository.findById(memberId);
		return member;
	}

	// 회원 이름 수정
	public void memberUpdate(Member member) {
		System.out.println("서비스 트랜잭션 확인 member::..." + member);
		memberJpaRepository.updateByMemeber(member);
		System.out.println("서비스 트랜잭션 확인 member After::..." + member);
		return;
	}

}
