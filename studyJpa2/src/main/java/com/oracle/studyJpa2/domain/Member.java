package com.oracle.studyJpa2.domain;

import org.thymeleaf.engine.TemplateHandlerAdapterMarkupHandler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name="member2")
@Data
@SequenceGenerator(
			name = "member_seq_gen",
			sequenceName = "member_seq_generator",
			initialValue = 1,
			allocationSize = 1
		)
public class Member {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "member_seq_gen"
			)
	@Column(name = "member_id", precision = 10)
	private Long id;
	@Column(name = "user_name", length = 50)
	private String name;
	private Long sal;
	
	//관계 설정 다대일
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;
	@Transient
	private String teamname;
}
