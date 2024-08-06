package com.oracle.oBootBoardWarPom;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoardWarPom.dao.BDao;
import com.oracle.oBootBoardWarPom.dao.JdbcDao;



@Configuration
public class SpringConfig {
	private final DataSource dataSource;
	public SpringConfig(DataSource dataSource) {
		 this.dataSource = dataSource;
	}
	
	@Bean
	public BDao jdbcDao() {
		return new JdbcDao(dataSource);
		//return new MemoryMemberRepository();
	}

}
