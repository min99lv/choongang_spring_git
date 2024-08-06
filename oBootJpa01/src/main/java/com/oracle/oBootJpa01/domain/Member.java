package com.oracle.oBootJpa01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member1")
@Getter
@Setter
@ToString //toString 메소드 대신 부탁
public class Member {
	@Id
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}

//	@Override
//	public String toString() {
//		String returnStr = "[id" + this.id + ", name:" + this.name + "]";
//		return returnStr;
//	}

}
