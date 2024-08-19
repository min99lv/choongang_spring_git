package com.oracle.oBootMybatis01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
	// com.oracle.oBootMybatis01.dao 패키지안의 EmpDao이름을 가진 모든 것
	@Pointcut("within(com.oracle.oBootMybatis01.dao.EmpDao*)")
	private void pointcutMethod() {

	}

	@Around("pointcutMethod()")
	// interCept => prehandler --> totalEmp, listEmp
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureStr = joinPoint.getSignature().toShortString();
		System.out.println(signatureStr + "is start---------");
		long st = System.currentTimeMillis();

		// interCept => 본문
		try {
			// 핵심 관심사 (Buz업무)
			Object obj = joinPoint.proceed();
			return obj;
			// interCept => posthandler
		} finally {
			long et = System.currentTimeMillis();
			System.out.println(signatureStr + "is finished....    ");
			System.out.println(signatureStr + "경과시간:   " + (et - st));
		}

	}

	@Before("pointcutMethod()")
	public void beforeMethod() {
		System.out.println("AOP beforeMethod start...  >> ");
	}
}
