package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
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
}
