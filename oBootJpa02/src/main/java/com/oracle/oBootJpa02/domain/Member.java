package com.oracle.oBootJpa02.domain;

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
@Table(name = "member2")
@Data // Getter, Setter, ToString
@SequenceGenerator(
			name = "member_seq_gen", // Seq 객체 
			sequenceName = "member_seq_generator", // Seq DB
			initialValue = 1,
			allocationSize = 1
		)
public class Member {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "member_seq_gen"
			
			)// id 시퀀스 설정
	@Column(name = "member_id", precision = 10)
	private Long id;
	@Column(name = "user_name", length = 50)
	private String name;
	private Long sal;
	
	// 관계설정 다대일
	@ManyToOne
	@JoinColumn(name = "team_id") // 어떤 fk?
	private Team team;
	// 실제 컬럼 X buffer 용도
	@Transient // 테이블로는 만들어지지 않지만 버퍼로 사용가능함 -> 컬럼 생성이 안됨
	private String teamname; // 팀 이름................
	@Transient 
	private Long teamid;
}
