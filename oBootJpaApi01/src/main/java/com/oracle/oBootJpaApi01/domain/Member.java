package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "member5")
// 1. Sequence  
@SequenceGenerator(
		name = "member_seq_gen5",
		sequenceName = "member_seq_generator5",
		initialValue = 1,
		allocationSize = 1
		)
public class Member {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "member_seq_gen5"
			)
	@Column(name="member_id")
	private Long id;
	@NotEmpty // 필수 체크 비워서 보내면 어케됨
	@Column(name = "userName")
	private String name;
	private Long sal;
	
	@ManyToOne // 연관관계 설정
	@JoinColumn(name="team_id")
	private Team team;
	
	
	
}
