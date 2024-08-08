package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor // 생성자
public class EmpDaoImpl implements EmpDao {

	// Mybatis DB연동
	private final SqlSession session;

	@Override
	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImp totalEmp start... ");

		try {
			totEmpCount = session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
			System.out.println("EmpDaoImp totalEmp totEmpCount::    " + totEmpCount);
		} catch (Exception e) {
			System.out.println("EmpDaoImp totalEmp e.getMessage" + e.getMessage());
		}
		return totEmpCount;
	}

	@Override
	public List<Emp> listEmpDao(Emp emp) {
		List<Emp> e
		return null;
	}
	
	

}
