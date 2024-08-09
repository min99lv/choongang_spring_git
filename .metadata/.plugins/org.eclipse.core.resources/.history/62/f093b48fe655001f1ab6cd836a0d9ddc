package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {

	private final EmpService es;

	@RequestMapping(value = "listEmpStart")
	public String listEmp(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp....");
		int totalEmp = es.totalEmp();
		
		model.addAttribute("totalEmp", totalEmp);
		return "list";
	}
	
	@RequestMapping(value="list")
	public String empList(Emp emp, Model model) {
		System.out.println("EmpController empList start....");
		List<Emp> listEmp = es.empList(emp);
		model.addAttribute("listEmp", listEmp);
		
		return "list";
	}
	
	

}
