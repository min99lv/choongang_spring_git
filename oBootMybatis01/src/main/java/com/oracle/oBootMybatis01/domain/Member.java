package com.oracle.oBootMybatis01.domain;

import java.util.Date;

import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity // jpa를 사용할 때 필수적 -> ENTITY매니저가 관리를 함
@Table(name="member3")
@Data
public class Member {
	@Id
	private Long id;
	private String name;
	private String password;
	@Column(nullable = false, columnDefinition = "date default sysdate")
	private Date reg_date = new Date();
}
