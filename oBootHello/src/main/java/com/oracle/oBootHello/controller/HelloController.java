package com.oracle.oBootHello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootHello.dto.Emp;

@Controller
public class HelloController {
	//
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	// Prifix -> templates 앞에
	// Suffic -> .html 뒤에
	@RequestMapping("hello") // 메소드를 기술 가능
	public String hello(Model model) {
		// 직접 관동성명을 해야함
		System.out.println("HelloContoller hello start....");
		// 시스템의 로그에 뿌려짐 -> 알아서 관동성명
		logger.info("start....");
		model.addAttribute("parameter", "boot start...");

		return "hello";
		// DispactherServlet -> viewResolver 호출 후 처리
		// viewResolver --> templates / + hello + .html
	}

	@ResponseBody // return할 때 view리절브가 실행되지 않고 HttpMessageConverter객체호출
					// StringConvertor호출 내부에서
					// String으로 응답
	@GetMapping("ajaxString") // requestMapping의 자손, get으로 받은 것 처리
	// ajaxName
	// http://localhost:8381/ajaxString?ajaxName=kkk
	public String ajaxString(@RequestParam("ajaxName") String aName) {
		System.out.println("HelloController ajaxString aName->" + aName);

		return aName;
		// view를 호출하지 않으므로 호출한 것의 body로 들어감
		// 받는 것이 따로 없으므로 브라우저로 들어감
	}

	@ResponseBody // 아작스를 호출하면 나를 호출한 놈의 몸통으로 들어감
	@GetMapping("ajaxEmp") // 어노테이션과 메소드명은 일반적으로 맞춰줌 안맞춰도됨
	public Emp ajaxEmp(@RequestParam("empno") String empno, 
						@RequestParam("ename") String ename) {
		System.out.println("HelloController ajaxEmp empno->" + empno);
		logger.info("ename->{}", ename);
		// 객체를 선언해서 객체를 돌려줌
		// HttpMessageConverter가 json Converter를 호출해서 객체로 돌려줌
		Emp emp = new Emp();
		emp.setEmpno(empno);
		emp.setEname(ename);

		return emp;
	}

}
