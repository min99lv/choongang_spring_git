package com.oracle.studyJpa2.controller;

import java.util.List;

import com.oracle.studyJpa2.domain.Member;
import com.oracle.studyJpa2.domain.Team;
import com.oracle.studyJpa2.repository.MemberRepository;

import jakarta.persistence.EntityManager;

public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;

	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Member memberSave(Member member) {
		Team team = new Team();
		team.setName(member.getTeamname());
		em.persist(team);
		return member;
	}

	// 회원 조회
	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
		return memberList;
	}

	// 회원 검색
	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

	@Override
	public void updateByMember(Member member) {
		
	}

}
