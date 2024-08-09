package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

	private final EmpDao ed;

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
		emp = ed.detailEmp(empno)	;	
		//emp = ed.detailEmp(emp.getEmpno());
	
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

}
