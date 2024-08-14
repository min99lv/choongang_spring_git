package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import oracle.net.aso.m;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {

	private final EntityManager em;

	// 회원 가입
	@Override
	public Member save(Member member) {
		System.out.println("MemberJpaRepositoryImpl save start...");
		em.persist(member);
		return member;
	}

	// 회원 목록 조회
	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll start.....");
		List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
		return memberList;
	}

	// 회원 아이디로 존재 확인
	@Override
	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaRepositoryImpl findById");
		Member member = em.find(Member.class, memberId);
		return Optional.ofNullable(member);
	}

	// 회원 수정
	@Override
	public void updateByMemeber(Member member) {
		// 1. update
		// merge --> 현재 Setting 된것만 수정, 나머지는 null
		em.merge(member);
		// 비밀번호에 null이 들어감

		// 2. update
//		System.out.println("레포지토리까지옴");
//		Member member3 = em.find(Member.class, member.getId());
//		member3.setName(member.getName());
//		member3.setId(member.getId());
//		System.out.println("레포지토리 member3");

		return;
	}
	
	
	

}
