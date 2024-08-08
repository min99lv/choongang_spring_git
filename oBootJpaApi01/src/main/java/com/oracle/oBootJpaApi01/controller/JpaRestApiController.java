package com.oracle.oBootJpaApi01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Controller + ResponsBody
@RestController
@Slf4j
// private static final Logger logger = LoggerFactory.getLogger(JpaReatApiController.class)
@RequiredArgsConstructor
public class JpaRestApiController {
	private final MemberService memberService;

	// test용도
	// String Convertor
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("JpaRestApiController start...");
		String hello = "안녕";
		return hello;
	}

	// Bad ApI
	// 객체 : Json Convertor
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1() {
		System.out.println("JpaRestApiController /restApi/v1/members start..");
		List<Member> listMember = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v1/members listMember.size()::   " + listMember.size());
		return listMember;
	}

	// Good API Easy Version
	// 목표 : 이름 & 급여만 전송
	@GetMapping("/restApi/v21/members")
	public Result membersVer21() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v21/members findMembers.size()::   " + findMembers.size());
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();

		for (Member member : findMembers) {
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());

			System.out.println(
					"JpaRestApiController /restApi/v21/members memberRtnDto.getName()::   " + memberRtnDto.getName());
			System.out.println(
					"JpaRestApiController /restApi/v21/members memberRtnDto.getSal() ::   " + memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("JpaRestApiController /restApi/v21/members resultList.size()::    " + resultList.size());
		return new Result(resultList.size(), resultList);
	}

	// Good API 람다 Version - 실무용
	// 목표 : 이름 & 급여만 전송
	@GetMapping("/restApi/v22/members")
	public Result membersVer22() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController /restApi/v21/members findMembers.size()::   " + findMembers.size());
		// 자바 8에서 추가한 스트림은 람다를 활용할 수 있는 기술 중 하나
		List<MemberRtnDto> memberCollect = findMembers.stream() // 람다 형식 > 를 말함 -> for문을 돌리지 않아도된다 m이 있는것만큼 내부적으로 돌아감
				.map(m -> new MemberRtnDto(m.getName(), m.getSal()))
				// 콜렉트로 모아서 리스트로 ~
				.collect(Collectors.toList());// 맵을 리스트로 반환해야하기때문에
		System.out.println("JpaRestApiController /restApi/v21/members memberCollect" + memberCollect.size());
		return new Result(memberCollect.size(), memberCollect);
	}

	@Data
	@AllArgsConstructor
	class Result<T> { // T는 어떤 데이터타입이든 내가 원하는 데이터 타입을 다 받아준다는 의미
		private final int totCount; // 총 인원 수 추가
		private final T data;
	}

	@Data
	@AllArgsConstructor
	class MemberRtnDto { // return 되는 값들만
		private String name;
		private Long sal;
	}

	// 0808 : 1. Bad API --> 원치 않는 항목까지 저장될 수 있으므로 BAD
	@PostMapping("/restApi/v1/memberSave")
	// @RequestBody : Json(member)으로 온것을 --> Member member Setting
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		System.out.println("JpaRestApiController /restApi/v1/memberSave member::   " + member);
		log.info("member member.getName()::  {}." + member.getName());
		log.info("member member.getSal():: {}." + member.getSal());

		Long id = memberService.saveMember(member);

		return new CreateMemberResponse(id); // 객체를 리턴하기 위해서
	}

	// 0808 : Good Api 필요한 항목만 저장할 수 있도록 함 GOOD ***CreateMemberRequest****를 통해서
	// 목적 : Entity Member member --> 직접 화면이나 API위한 Setting 금지
	// 예시 : @NotEmpty --> @Column(name = "userName")
	@PostMapping("/restApi/v2/memberSave")
	// @RequestBody : Json(member)으로 온것을 --> Member member Setting
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid CreateMemberRequest cMember) {
		System.out.println("JpaRestApiController /restApi/v2/memberSave member::   " + cMember);
		log.info("member member.getName()::  {}." + cMember.getName());
		log.info("member member.getSal():: {}." + cMember.getSal());
		// 받은 값에서 이름하고 급여만 넘김
		Member member = new Member();
		member.setName(cMember.getName());
		member.setSal(cMember.getSal());

		Long id = memberService.saveMember(member);

		return new CreateMemberResponse(id); // 객체를 리턴하기 위해서
	}

	@Data
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
		private Long sal;
	}

	@Data
	@RequiredArgsConstructor
	class CreateMemberResponse {
		private final Long id;

//		public CreateMemberResponse(Long id) {
//			this.id = id;
//		}
	}

	/*
	 * 단일 Id 조회 API --> get Mapping URI 상에서 '{ }' 로 감싸여있는 부분과 동일한 변수명을 사용하는 방법 해당
	 * 데이터가 있으면 업데이트를 하기에 Get요청이 여러번 실행되어도 해당 데이터는 같은 상태이기에 멱등
	 */
	// @pathVariable : 파라메터로 가져온 id를 long id라 하겠다
	@GetMapping("/restApi/v15/members/{id}")
	public Member membersVer15(@PathVariable("id") Long id) {
		System.out.println("JpaRestApiController /restApi/v15/members id::    " + id);
		Member findMember = memberService.findByMember(id);
		System.out.println("JpaRestApiController /restApi/v15/members findMember::    " + findMember);

		return findMember;
	}

	@PutMapping("/restApi/v21/members/{id}")
	public UpdateMemberResponse updateMember21(@PathVariable("id") Long id,
			@RequestBody @Valid UpdateMemberRequest uMember) {

		System.out.println("JpaRestApiController updateMember21 id::   " + id);
		System.out.println("JpaRestApiController updateMember21 uMember::   " + uMember);
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		Member findMember = memberService.findByMember(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
	}

	@Data
	static class UpdateMemberRequest {
		private String name;
		private Long sal;
	}

	@Data
	@AllArgsConstructor
	class UpdateMemberResponse {
		private Long id;
		private String name;
		private Long sal;
	}

}
