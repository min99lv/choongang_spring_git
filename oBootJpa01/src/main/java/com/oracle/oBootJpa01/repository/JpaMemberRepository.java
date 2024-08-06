package com.oracle.oBootJpa01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMemberRepository implements MemberRepository {
	// JPA DML --> EntityManager 필수 ***
	private final EntityManager em;

	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	// 회원가입
	@Override
	public Member memberSave(Member member) {
		em.persist(member); // 저장하는 메소드
		System.out.println("JpaMemberRepository memberSave after..");
		return member;
	}

	// 회원 조회
	@Override
	public List<Member> findAllMember() { // from Member는 테이블이름이 아니고 객체이름
		List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList(); // list로 보내기
																											// 위해서 작성
		System.out.println("JpaMemberRepository findAllMember memberList.size()--->>>>" + memberList.size());
		return memberList;
	}
	// 회원 검색
	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + '%';
		System.out.println("JpaMemberRepository findByNames pname... " + pname);
		List<Member> memberList = em.createQuery("select m from Member m where name like :name",Member.class)
									.setParameter("name", pname)
									.getResultList()
									;
		System.out.println("JpaMemberRepository findByNames memberList.size()...."+memberList.size());		
		return memberList;
	}

}
