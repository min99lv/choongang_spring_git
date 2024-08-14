package com.oracle.oBootMybatis01.service;

import java.lang.reflect.Method;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SampleInterceptor implements HandlerInterceptor {
	public SampleInterceptor() {

	}

	// 3번
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			// model과 veiw 같이 쓰여지는 객체
			ModelAndView modelAndView) throws Exception {
		System.out.println("post Handler....................");
		String ID = (String) modelAndView.getModel().get("id");
		int memCnt = (Integer) modelAndView.getModel().get("memCnt");
		System.out.println("SampleInterceptor post handler memCnt: " + memCnt);
		if (memCnt < 1) {
			System.out.println("memCnt Not exists");
			request.getSession().setAttribute("ID", ID);
			// user가 존재하지 않으면 user interceptor page(회원 등록)이동
			response.sendRedirect("doMemberWrite");
		} else { // 정상 login user
			System.out.println("memCnt exists...");
			request.getSession().setAttribute("ID", ID);
			// user가 존재하면 user interCepttor page(회원 list)이동
			response.sendRedirect("doMemberList");
		}
	}

	// 1번 젤 먼저 수행됨
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("Pre handler......................");
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		System.out.println("Bean :" + method.getBean());
		System.out.println("Mehod :" + methodObj);

		return true;

	}

}
