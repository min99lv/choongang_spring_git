package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {

	// mybatis 연동
	private final SqlSession session;

	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		try {
			deptList = session.selectList("tkSelectDept");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return deptList;
	}

	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpl insertDept Start....");
		session.selectOne("procDeptInsert", deptVO);

	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImpl selListDept start...");
		// ResultMap은 DB 컬럼명과 DTO의 변수 명이 다를 때 사용
		session.selectOne("procDeptList", map);

	}

}
