package com.oracle.oBootDbConnnect.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDbConnnect.domain.Member7;


public interface MemberRepository {
	Member7 save(Member7 member7);

	List<Member7> findAll(); //Member7의 모든 요소를 조회
}
