package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

	private final EmpDao ed;
	private final DeptDao dd;
	// dao를 여러개 선언 가능 -> interface를 하나만 상속 받아야함

	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl totalEmp start...");
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp totEmpCnt::    " + totEmpCnt);
		return totEmpCnt;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		System.out.println("EmpServiceImpl listEmp start..");
		List<Emp> empList = null;
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()" + empList.size());
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println(" detail service start....");
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//		
//		2. EmpDao   detailEmp method 선언 
////		                    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
		Emp emp = null;
		emp = ed.detailEmp(empno);
		// emp = ed.detailEmp(emp.getEmpno());

		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)

		System.out.println("EmpServiceImpl updateEmp start...");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);

		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		System.out.println("EmpServiceImpl listManager Start....");
		List<Emp> empList = null;
		empList = ed.listManager();
		System.out.println("EmpServiceImpl listManager empList.size()::      " + empList.size());
		return empList;
	}

	@Override
	public List<Dept> deptSelect() {
		System.out.println("EmpServiceImpl deptSelect Start....");
		List<Dept> deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl listManager deptList.size()::      " + deptList.size());
		return deptList;
	}

	@Override
	public int insertEmp(Emp emp) {
		int insertResult = 0;
		insertResult = ed.insertEmp(emp);
		return insertResult;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		result = ed.deleteEmp(empno);
		System.out.println("result 서비스" + result);
		return result;
	}

	@Override
	public int condTotalEmp(Emp emp) {
		System.out.println("EmpServiceImpl condTotalEmp start total.....");
		int totEmpCnt = ed.condTotalEmp(emp);
		System.out.println("EmpServiceImpl totalEmp totEmpCnt::   " + totEmpCnt);
		return totEmpCnt;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> listSearchEmp = ed.listSearchEmp(emp);

		return listSearchEmp;
	}

}
