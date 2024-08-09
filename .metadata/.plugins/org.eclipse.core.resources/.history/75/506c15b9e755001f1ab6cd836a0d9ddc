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
	public List<Emp> empList(Emp emp) {
		
		List<Emp> listEmp = ed.listEmpDao(emp);
		
		return listEmp;
	}

}
