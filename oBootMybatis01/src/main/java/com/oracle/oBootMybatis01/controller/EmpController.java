package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {

	private final EmpService es;

	@RequestMapping(value = "listEmpStart")
	public String listEmpStart(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp....");
		// 21
		int totalEmp = es.totalEmp();
		String currenPage = "1";

		// paging 작업
		Paging page = new Paging(totalEmp, currenPage);
		// parameter emp ---> Page만 추가 setting
		emp.setStart(page.getStart()); // 시작시 1
		emp.setEnd(page.getEnd()); // 시작시 10

		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()::   " + listEmp.size());

		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);

		return "list";
	}

	@RequestMapping(value = "listEmp")
	public String listEmp(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp....");
		// 21
		int totalEmp = es.totalEmp();
		System.out.println("EmpController listEmp emp->" + emp);
		// paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		// parameter emp ---> Page만 추가 setting
		emp.setStart(page.getStart()); // 시작시 1
		emp.setEnd(page.getEnd()); // 시작시 10

		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()::   " + listEmp.size());

		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);

		return "list";
	}

	// 특정 사원 정보 조회
	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start detailEmp");

//		Emp emp = new Emp();
//		emp.setEmpno(emp1.getEmpno());
//		emp.setEname(emp1.getEname());
//		emp = es.detailEmp(emp);
//				1. EmpService안에 detailEmp method 선언
//				   1) parameter : empno
//				   2) Return      Emp
//		
//				2. EmpDao   detailEmp method 선언 
////				                    mapper ID   ,    Parameter
//				emp = session.selectOne("tkEmpSelOne",    empno);

		// 강사님 코드
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("emp", emp);
		return "detailEmp";
	}

	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController updateFormEmp start....");
		Emp emp = es.detailEmp(emp1.getEmpno());

		System.out.println("EmpController updateFormEmp emp::       " + emp);
		// 문제
		// 1. DTO String hiredate
		// 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
		// 3.해결책 : 년월일만 짤라 넣어 주어야 함
		String hiredate = "";
		if (emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
		}
		System.out.println("hiredate-->" + hiredate);
		model.addAttribute("emp", emp);

		return "updateFormEmp";
	}

	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp start....");
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//  	 2. EmpDao updateEmp method 선언
//          						mapper ID   ,    Parameter
//  	 updateCount = session.update("tkEmpUpdate",   emp);
		int updateCount = 0;
		updateCount = es.updateEmp(emp);
		System.out.println("컨트롤러 updateEmp updateCount::    " + updateCount);
		model.addAttribute("uptCnt", updateCount); // Test Controller간 Data 전달
		model.addAttribute("kk3", "Message Test"); // Test Controller간 Data 전달
		return "redirect:listEmp"; // 같은 컨트롤러안에있는 메소드를 찾아감
	}

}
