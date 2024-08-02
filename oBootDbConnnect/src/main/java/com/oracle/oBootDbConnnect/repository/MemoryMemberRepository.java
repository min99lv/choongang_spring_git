package com.oracle.oBootDbConnnect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDbConnnect.domain.Member7;

/*@Repository*/

public class MemoryMemberRepository implements MemberRepository {

	private static Map<Long, Member7> store = new HashMap<Long, Member7>();
	private static long sequence = 0L;

	@Override
	public Member7 save(Member7 member7) {
		System.out.println("MemoryMemberRepository save start..");
		member7.setId(++sequence);
		store.put(member7.getId(), member7);
		return member7;
	}

	@Override
	public List<Member7> findAll() {
		System.out.println("MemoryMemberRepository find All start..");
		return new ArrayList<>(store.values());
	}

}
