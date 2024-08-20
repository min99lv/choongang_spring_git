package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {
	private final PlatformTransactionManager transactionManager;

	private final SqlSession session;
	

	@Override
	public int memCount(String id) {
		// Mapper --> member1.xml
		// result = session.selectOne("memCount", id);
		int result = 0;
		try {
			result = session.selectOne("memCount", id);

		} catch (Exception e) {
			System.out.println("result" + result);
		}
		return result;
	}

	@Override
	public List<Member1> listMem(Member1 member1) {
		List<Member1> listMem = session.selectList("listMember1", member1);
		System.out.println("다오 옴 .. listMem size::" + listMem.size());
		return listMem;
	}
	// 트랜잭션 처리 - mybatis 실패 경우

	@Override
	public int transactionInsertUpdate() {
		int result = 0;
		System.out.println("transactionInsertUpdate start...");
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();

		try {
			// 두개의 transaction Test 성공과 실패
			// 결론 --> Sqlsession 은 하나 실행 할 때마다 자동 commit
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("강유6");

			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate member1 result->" + result);
			// 실행했을 때 member1 result -> 1 
			// 같은 pk로 실패 유도
			// 유니크 제약조건에 의해서 실패 
			// 같은 트랜잭션을 돌릴때 치명타 
			member2.setId("1005");
			member2.setPassword("2345");
			member2.setName("강유6");
			result = session.insert("insertMember1", member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate member2 result->" + result);

		} catch (Exception e) {
			System.out.println("Member1DaoImpl transactionInsertUpdate Exception" + e.getMessage());
			result = -1;
		}
		return result;
	}
	

	// 트랜잭션 성공 경우
	@Override
	public int transactionInsertUpdate3() {
		int result = 0;
		System.out.println("transactionInsertUpdate3 start...");
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		// 두개의 트랜잭션을 하나의 트랜잭션으로 묶고싶은 것 
		try {
			// 두개의 transaction Test 성공과 실패
			// 결론 --> Sqlsession 은 하나 실행 할 때마다 자동 commit
			// Transaction관리는 transactionManager의 getTransaction을가지고 상태따라 설정
			member1.setId("1009");
			member1.setPassword("2345");
			member1.setName("강유6");

			
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member1 result->" + result);
			// 실행했을 때 member1 result -> 1 
			// 같은 pk로 실패 유도
			// 유니크 제약조건에 의해서 실패 
			// 같은 트랜잭션을 돌릴때 치명타 
			member2.setId("1010");
			member2.setPassword("3457");
			member2.setName("이순신7");
			result = session.insert("insertMember1", member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member2 result->" + result);
			transactionManager.commit(txStatus); // 커밋이냐 롤백이냐 member1, member2 같이 행동
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("Member1DaoImpl transactio nInsertUpdate3 Exception" + e.getMessage());
			result = -1;
		}
		return result;
		
	}

}
