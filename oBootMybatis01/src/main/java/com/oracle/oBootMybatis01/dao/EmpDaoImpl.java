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
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;

		try {
			System.out.println("DAO listEmp emp->" + emp);

			// Map Id , parameter
			empList = session.selectList("tkEmpListAll", emp);
			System.out.println("EmpDaoImpl listEmp empList.size()::   " + empList.size());
		} catch (Exception e) {
			e.getMessage();
		}

		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("dao start...");
		Emp emp = new Emp();
		try {

			emp = session.selectOne("tkEmpSelOne", empno);
			System.out.println("detailEmp list emp::" + emp);
		} catch (Exception e) {
			System.out.println("다오 오류" + e.getMessage());
		}
		// 2. EmpDao detailEmp method 선언
		//// mapper ID , Parameter
		// emp = session.selectOne("tkEmpSelOne", empno);
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		int updateCount = 0;
		System.out.println("다오시작");
		try {
			updateCount = session.update("tkEmpUpdate", emp);

		} catch (Exception e) {
			System.out.println("다오 오류" + e.getMessage());
		}
		return updateCount;
	}

}
