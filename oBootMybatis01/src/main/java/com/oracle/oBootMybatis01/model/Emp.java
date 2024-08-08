package com.oracle.oBootMybatis01.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Emp {
	private int empno;
	@NotEmpty(message = "이름은 필수 입니다 !!") // jsp에서 메세지를 받음
	private String ename;
	private String job;
	private int mgr;
	private String hiredate; // 계속 날짜를 수정해가면서 저장하는 것은 String
	private int sal;
	private int comm;
	private int deptno;
	
	// 조회용
	private String search;
	private String keyword;
	private String pageNum;
	private int start;
	private int end;
	// page 정보
	private String currentPage;
}
