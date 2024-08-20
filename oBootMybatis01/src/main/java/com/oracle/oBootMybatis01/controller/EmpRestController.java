package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

// @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class EmpRestController {
	private final EmpService es;
 
	// Ajax - StringConverter
	@RequestMapping("/helloText")
	public String hellogText() {
		System.out.println("EmpRestController start....");
		String hello = "안녕";
		// Http Message converter -> StringConverter
		return hello;
	}

	// Ajax - JsonConverter
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendVO2(Dept dept) {
		System.out.println("@RestController dept.getDeptno()" + dept.getDeptno());
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(dept.getDeptno());

		return vo;
		// http://jsonviewer.stack.hu/
	}

	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3() {
		System.out.println("@RestController sendVO3 Start....");
		List<Dept> deptList = es.deptSelect();
		return deptList;
	}
	// 	결과 Text로 보내줌
	@RequestMapping(value="/empnoDelete")
	public String empnoDelete(Emp emp){
		System.out.println("@rRestController start,,,,,");
		int delStatus = 0;
		
		delStatus = es.deleteEmp(emp.getEmpno());
		String delStatusStr = Integer.toString(delStatus);
		return delStatusStr;
	}
	// 결과 객체로 본재무
	@RequestMapping(value="/empnoDelete03")
	public Map<String, Object> empnoDelete03(Emp emp){
		System.out.println("@rRestController Empno03 start,,,,,");
		int delStatus = 0;
		
		delStatus = es.deleteEmp(emp.getEmpno());
		//String delStatusStr = Integer.toString(delStatus);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("delStatus", delStatus);
		
		return resultMap;
	}
}
