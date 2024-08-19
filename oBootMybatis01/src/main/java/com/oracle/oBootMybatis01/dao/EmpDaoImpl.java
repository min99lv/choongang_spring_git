package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

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

	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listManager start....");
		try {
			// emp 관리자만 Select Naming Rure = tkSelectManager
			empList = session.selectList("tkSelectManager");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return empList;
	}

	@Override
	public int insertEmp(Emp emp) {
		int insertResult = 0;
		System.out.println("인서트 다오 시작 ");
		try {
			insertResult = session.insert("tkInsert", emp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("인서트 다오 리턴 값:" + insertResult);
		return insertResult;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		try {
			result = session.delete("deleteEmp", empno);
			System.out.println("result 다오" + result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public int condTotalEmp(Emp emp) {
		int totEmpCount = 0;

		try {
			totEmpCount = session.selectOne("condEmpTotal", emp);
			System.out.println("다오까지 옴.. totEmpCount" + totEmpCount);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return totEmpCount;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> listSearchEmp = session.selectList("tkEmpSearchList3", emp);
		return listSearchEmp;
	}

	// 부서 정보
	@Override
	public List<EmpDept> listEmpDept() {
		List<EmpDept> listEmpDept = null;
		System.out.println("EmpDaoImpl listEmpDept start.....");
		listEmpDept = session.selectList("tkListEmpDept");
		System.out.println("EmpDaoImpl listEmpDept listEmpDept.size()" + listEmpDept.size());
		return listEmpDept;
	}

	// ajax를 이용해서 dname 가져오기
	@Override
	public String EmpDept(int deptno) {
		System.out.println("EmpDaoImpl EmpDept start....");
		String deptName = "";
		try {
			System.out.println("EmpDaoImpl EmpDept deptno__:" + deptno);
			deptName = session.selectOne("tkDeptName", deptno);
			System.out.println("EmpDaoImpl EmpDept deptName__:" + deptName);

		} catch (Exception e) {
			System.out.println("EmpDaoImpl EmpDept Exception" + e.getMessage());
		}
		return deptName;
	}

}
