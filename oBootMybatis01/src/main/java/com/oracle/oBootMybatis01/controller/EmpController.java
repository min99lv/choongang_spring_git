package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {

	private final EmpService es;
	// MIME(영어: Multipurpose Internet Mail Extensions)
	// 전자 우편을 위한 인터넷 표준 포맷
	// MIME을 구현하기 위한 객체 JAVAMAILSENDER
	private final JavaMailSender mailSender;

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
		return "forward:listEmp"; // 같은 컨트롤러안에있는 메소드를 찾아감
		// return "redirect:listEmp"; // 같은 컨트롤러안에있는 메소드를 찾아감
	}

	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("EmpController writeFormEmp start.......");
		// 관리자 사번만 Get
		List<Emp> empList = es.listManager();
		// 1. service -> listManager
		// 2. Dao -> listManager
		// 3. Mapper -> tkSelectManager
		System.out.println(" EmpController writeFormEmp empList.size::   " + empList.size()); //
		model.addAttribute("empMngList", empList); // emp Manager List

		// 부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp deptList.size():: " + deptList.size());

		return "writeFormEmp";
	}

	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp, Model model) {
		// Service, Dao , Mapper명[insertEmp] 까지 -> insert
		int insertResult = es.insertEmp(emp);
		System.out.println("insert controller" + insertResult);
		if (insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp";
		}

	}

	// validation writeFormEmp3 check

	@RequestMapping(value = "writeFormEmp3")
	public String writeFormEmp3(Model model) {
		System.out.println("EmpController writeFormEmp start.......");
		// 관리자 사번만 Get
		List<Emp> empList = es.listManager();
		// 1. service -> listManager
		// 2. Dao -> listManager
		// 3. Mapper -> tkSelectManager
		System.out.println(" EmpController writeFormEmp3 empList.size::   " + empList.size()); //
		model.addAttribute("empMngList", empList); // emp Manager List

		// 부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp3 deptList.size():: " + deptList.size());

		return "writeFormEmp3";
	}

	// Validation시 참조
	@PostMapping(value = "writeEmp3")
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp, BindingResult result, Model model) {
		// Service, Dao , Mapper명[insertEmp] 까지 -> insert

		// Validation 오류시 Result
		if (result.hasErrors()) {
			System.out.println("EmpController writeEmp3 hasErrors...");
			model.addAttribute("msg", "BindingResult 입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		int insertResult = es.insertEmp(emp);

		if (insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}

	}

	@GetMapping(value = "confirm")
	public String confirm(Emp emp1, Model model) {
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("empno", emp1.getEmpno());
		if (emp != null) {
			System.out.println("empConroller confirm 중복된 사번");
			model.addAttribute("msg", "중복된 사번입니다.");
			// return "forward:writeFormEmp";
		} else {
			System.out.println("empConroller confirm 사용 가능한 사번");
			model.addAttribute("msg", "사용 가능한 사번 입니다");
			// return "forward:writeFormEmp";
		}
		return "forward:writeFormEmp"; // 좋은 return
	}

	@RequestMapping(value = "deleteEmp")
	// Controller --> deleteEmp 1.parameter : empno
	// name -> Service, dao , mapper
	// return -> listEmp
	public String deleteEmp(Emp emp, Model model) {
		int result = es.deleteEmp(emp.getEmpno());
		System.out.println("result 컨트롤러" + result);
		return "redirect:listEmp";
	}

	// 조건 조회
	@RequestMapping(value = "listSearch3")
	public String listSearch3(Emp emp, Model model) {
		System.out.println("empConroller start listEmp...");
		System.out.println("empConroller listSearch3 emp..." + emp);
		// Emp 전체 count
		int totalEmp = es.condTotalEmp(emp);
		System.out.println("empConroller listSearch3 totalEmp=>" + totalEmp);
		// paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		// parameter emp --> page만 추가 setting
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());
		System.out.println("컨트롤러 페이지 :   " + page);
		// 1. DAO ed.empSearchList3(emp);
		// 2. Mapper selectList("tkEmpSearchList3", emp);

		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		System.out.println("empConroller listSearch3 listSearchEmp,size()::    " + listSearchEmp.size());
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listSearchEmp);
		model.addAttribute("page", page);

		return "list";
	}
	// 부서 정보

	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		System.out.println("EmpController listEmpDept start..");
		// Service ,DAO -> listEmpDept
		// Mapper만 ->EmpDept.xml(tkListEmpDept)
		List<EmpDept> listEmpDept = es.listEmpDept();
		model.addAttribute("listEmpDept", listEmpDept);
		return "listEmpDept";
	}

	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("mailSending");
		String tomail = "wjddksidsid@gmail.com"; // 받는 사람 이메일
		String setfrom = "fantamasitta@gmail.com";
		String title = "mailTransport 입니다"; // 제목

		try {
			// Mime 전자우편 Internet 표준 Format
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom); // 보내는 사람 생량하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); // 받는 사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			String tempPassword = (int) (Math.random() * 999999) + 1 + ""; // 메일내용
			messageHelper.setText("임시비밀번호 입니다" + tempPassword);
			System.out.println("임시 비밀번호 입니다 " + tempPassword);

			mailSender.send(message);
			model.addAttribute("check", 1); // 정상전달

			// DB Logic

		} catch (Exception e) {
			System.out.println("MailTranport e.getMessage" + e.getMessage());
			model.addAttribute("check", 2);
		}
		return "mailResult";
	}

	// pl/sql
	// Procedure Test 입력화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("writeDeptIn Start");
		return "writeDept3";
	}

	// Procedure 통한 Dept 입력후 VO 전달
	@PostMapping(value = "writeDept")
	public String writeDept(DeptVO deptVO, Model model) {
		es.insertDept(deptVO);
		if (deptVO == null) {
			System.out.println("deptVo NULL");

		} else {
			System.out.println("deptVO.getOdeptno()::  " + deptVO.getOdeptno());
			System.out.println("deptVO.getOdname()::  " + deptVO.getOdname());
			System.out.println("deptVO.getOloc()::  " + deptVO.getOloc());
			model.addAttribute("msg", "정상 입력 되었습니다");
			model.addAttribute("dept", deptVO);
		}
		return "writeDept3";
	}

	// pl / sql
	// 부서 조회
	@GetMapping(value = "writeDeptCursor")
	public String writeDeptCursor(Model model) {
		System.out.println("EmpController writeDeptCursor Start..");
		// 부서 범위 조회
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sDeptno", 10);
		map.put("eDeptno", 55);

		es.selListDept(map);
		List<Dept> deptLists = (List<Dept>) map.get("dept");
		for (Dept dept : deptLists) {
			System.out.println("dept.getDname::...." + dept.getDname());
			System.out.println("dept.getDLoc::...." + dept.getLoc());
		}
		System.out.println("deptLists.size()" + deptLists.size());
		model.addAttribute("deptLists", deptLists);

		return "writeDeptCursor";
	}

	// 인터셉터 시작화면
	@RequestMapping(value = "interCeptorForm")
	public String interCeptorForm() {
		System.out.println("interCeptorForm start...");
		return "interCeptorForm";
	}

	// 2번 InterCeptor Number2
	@RequestMapping(value = "interCeptor")
	public String interCeptor(Member1 member1, Model model) {
		System.out.println("EmpController interCeptor Test start..");
		System.out.println("EmpController interCeptor member1.getId()::    " + member1.getId());
		// 존재 : 1, 비존재 : 0
		int memCnt = es.memCount(member1.getId());
		System.out.println();

		model.addAttribute("id", member1.getId());
		model.addAttribute("memCnt", memCnt);
		System.out.println("interCeptor Test End");
		return "interCeptor"; // user 존재하면 user 이용 조회 page --> 형식적으로 쓴 것
		// 진짜 이동하는 것 SampleInterceptor...안에 뷰
	}

	@RequestMapping(value = "doMemberWrite")
	public String doMemberWrite(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite부터 하세요");
		model.addAttribute("id", ID);
		return "doMemberWrite";
	}

	@RequestMapping(value = "doMemberList")
	public String doMemberList(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberList Test Start ID->" + ID);
		Member1 member1 = null;

		List<Member1> listMem = es.listMem(member1);
		model.addAttribute("ID", ID);
		model.addAttribute("listMem", listMem);
		return "doMemberList";

	}

	// ajaxForm Test 입력화면
	@RequestMapping(value = "ajaxForm")
	public String ajaxForm(Model model) {
		System.out.println("ajaxForm start....");
		return "ajaxForm";
	}

	@ResponseBody
	@RequestMapping(value = "getDeptName")
	public String getDeptName(Dept dept, Model model) {
		System.out.println("deptno__:" + dept.getDeptno());
		String deptName = es.deptName(dept.getDeptno());
		System.out.println("deptName__:" + deptName);
		return deptName;
	}

	// Ajax List Test
	@RequestMapping(value = "listEmpAjaxForm")
	public String listEmpAjaxForm(Model model) {
		Emp emp = new Emp();
		System.out.println("Ajax List Test Start!!");
		// parameter Emp -> page만 추가 setting
		emp.setStart(1); // 시작시 1
		emp.setEnd(10); // 시작시 10

		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpCont roller listEmpAjax listEmp.size()" + listEmp.size());
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);

		return "listEmpAjaxForm";
	}

	@ResponseBody
	@RequestMapping(value = "empSerializeWrite")
	public Map<String, Object> empSerializeWrite(@RequestBody @Valid Emp emp) {
		System.out.println("EmpController start...");
		System.out.println("EmpController emp->" + emp);
		int writeResult = 1;

		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("EmpController empSerializeWrite writeResult-->" + writeResult);

		resultMap.put("writeResult", writeResult);
		return resultMap;

	}

	// listEmpAjaxForm2
	@RequestMapping(value = "listEmpAjaxForm2")
	public String listEmpAjaxForm2(Model model) {
		System.out.println("listEmpAjaxForm2 start...");
		Emp emp = new Emp();
		// parameter emp --> Page만 추가 Setting
		emp.setStart(1);// 시작시 1
		emp.setEnd(15); // 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("listEmpAjaxForm2 listEmp.size()" + listEmp.size());
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm2";
	}

	// listEmpAjaxForm3
	@RequestMapping(value = "listEmpAjaxForm3")
	public String listEmpAjaxForm3(Model model) {
		System.out.println("listEmpAjaxForm2 start...");
		Emp emp = new Emp();
		// parameter emp --> Page만 추가 Setting
		emp.setStart(1);// 시작시 1
		emp.setEnd(15); // 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("listEmpAjaxForm3 listEmp.size()" + listEmp.size());
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm3";
	}

	@ResponseBody
	@RequestMapping(value = "empListUpdate")
	public Map<String, Object> empListUpdate(@RequestBody @Valid List<Emp> listEmp) {
		System.out.println("empListUpdate start...");
		int updateResult = 1;

		for (Emp emp : listEmp) {
			System.out.println("EmpController empListUpdate emp->" + emp);
			// 업데이트 로직 굳이 구현하지 않는다.
			// int writeResult = kkk.listUpdateEmp(emp);

		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("updateResult", updateResult);
		return resultMap;
	}

	// mybatis Transaction 처리
	@ResponseBody
	@RequestMapping(value = "transactionInsertUpdate")
	public String transactionInsertUpdate(Emp emp, Model model) {
		System.out.println("Empcontroller transactionInsertUpdate start..");
		// member Insert 성공과 실패
		int returnMember = es.transactionInsertUpdate();
		System.out.println("Empcontroller transactionInsertUpdate returnMember=>" + returnMember);

		String returnMemberString = String.valueOf(returnMember);

		return returnMemberString;
	}
}
