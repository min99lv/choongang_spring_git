package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.Bdao;
import com.oracle.oBootBoard.dao.JdbcDao;

@Configuration // 제일 먼저 import되는 환경 설정
public class SpringConfig {
	private DataSource dataSource;
	
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Bean
	public Bdao JdbcDao () {
		
		return new JdbcDao(dataSource);
		// return new MemoryBoardRepository(); 존재한다면 교체 가능
	}
	
}
