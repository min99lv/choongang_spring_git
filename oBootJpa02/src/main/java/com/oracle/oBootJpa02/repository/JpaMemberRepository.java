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
		member.setTeam(team); // fk를 맞춰춘다 팀에 ....
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
		// 프라이머리키를 이용해서 객체를 검색한 것을 돌려줌
		Member member = em.find(Member.class, memberId);

		return member;
	}
	
	// 회원 수정
	@Override
	public int updateByMember(Member member) {
		int result = 0;
		System.out.println("JpaMemberRepository updateByMember 1member-->>>>>" + member);
		Member member3 = em.find(Member.class, member.getId());
		// 존재하면 수정
		if (member3 != null) {
			// 팀 저장
			System.out.println("JpaMemberRepository updateByMember member.getTeamid()-->>>>>" + member.getTeamid());
			Team team = em.find(Team.class, member.getTeamid());
			if (team != null) {
				team.setName(member.getTeamname());
				em.persist(team);
			}
			// 회원 저장
			member3.setTeam(team);
			member3.setName(member.getName());
			em.persist(member3);
			result = 1;
		} else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist...");
		}
		return result;

	}

	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + '%';
		List<Member> memberList = em.createQuery("select m from Member m where name like :name", Member.class)
				.setParameter("name", pname)
				.getResultList();
		return memberList;
	}

	@Override
	public List<Member> findByIdSal(Member member) {
		List<Member> memberList = em.createQuery("select m from Member m where id> :id and sal >:sal",Member.class)
				.setParameter("id", member.getId())
				.setParameter("sal", member.getSal())
				.getResultList();
		return memberList;
	}

}
