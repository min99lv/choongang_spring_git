package com.oracle.oBootJpa02.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;

	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	// 회원 저장
	@Override
	public Member memberSave(Member member) {
		// 1. 팀 저장
		Team team = new Team();
		team.setName(member.getTeamname());
		em.persist(team);
		// 2. 회원 저장 
		member.setTeam(team);  // fk를 맞춰춘다 팀에 ....
		em.persist(member);
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
