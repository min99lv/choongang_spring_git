package com.oracle.oBootDbConnnect;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDbConnnect.repository.JdbcMemberRepository;
import com.oracle.oBootDbConnnect.repository.MemberRepository;
import com.oracle.oBootDbConnnect.repository.MemoryMemberRepository;

@Configuration // 가장 먼저 실행됨
public class SpringConfig {
	private DataSource dataSource; //메모리에 떠 있는 상태

	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// 모두 사용 public
	@Bean // bean찾기 때문에 설정해주어야함
	public MemberRepository memberRepository() {
//Orcle  부품교체 
//		return new JdbcMemberRepository(dataSource);
//Memory 부품교체 -> 기본 생성자밖에 없기 때문에 매개변수값을 넣지 않아도 된다
		return new MemoryMemberRepository();
	}
}
