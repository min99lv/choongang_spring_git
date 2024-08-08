package com.oracle.studyJpa2.repository;

import java.util.List;

import com.oracle.studyJpa2.domain.Member;

public interface MemberRepository {
	Member memberSave(Member member);
	List<Member> findAll();
	Member findByMember(Long memberId);
	void updateByMember(Member member);
}
