package com.oracle.oBootMybatis01.model;

import lombok.Data;

// join 목적
@Data
public class EmpDept {
	// Emp용
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private int sal;
	private int comm;
	private int deptno;
	
	// Dept (컬럼이 많다는 가정 ... 별로 없다면 Emp테이블에 추가해도 되는 것임)
	private String dname;
	private String loc;
}
