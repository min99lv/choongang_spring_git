<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	
	// listEmpAjaxForm3.jsp 에서 수정전 작업
	function getEmpListUpdateTest() {
		alert("getEmpListUpdateTest RUN....")
		// group번호 가져오기
		var arr = new Array();
		var item;
		// 한로우 한로우 item에 저장 -> 가져온것을 그대로 뽑아서 사용
		<c:forEach items="${listEmp}" var="item">
		arr.push({
			empno : "${item.empno}",
			ename : "${item.ename}",
			deptno : "${item.deptno}"
		});
		</c:forEach>

		for (var i = 0; i < arr.length;) {
			alert("arr.empno->" + i + ":" + arr[i].empno + "   arr.ename->" + i
					+ ":" + arr[i].ename);
			i++;
			if (i > 2)
				return;
		}
	}
	// listEmpAjaxForm3.jsp 에서 수정후 작업
	//java script에서 emp list를 수정한 데이터를 json데이터로 전환 후 empController로 보내보기 위한 예제
	function getEmpListUpdate() {
		alert('getEmpListUpdate run...');
		let empList = []; // 직원 데이터를 저장할 빈 배열 생성
		const inputs = document
				.querySelectorAll('input[name="empno"], input[name="ename"],input[name="deptno"]');
		for (let i = 0; i < inputs.length; i += 3) {
			const empno = inputs[i + 1].value;
			const ename = inputs[i + 2].value;
			const deptno = inputs[i + 3].value;
			//불러온 값들을 json으로 만든다
			const empItem = {
				"empno" : empno,
				"ename" : ename,
				"deptno" : deptno
			};
			//alert('ename >> '+ename);
			//json객체를 배열에 넣어둔다
			empList.push(empItem);
			if (i > 5) {
				break;
			}
		}
		alert('JSON.stringify(empList) >> ' + JSON.stringify(empList));

		if (empList.length > 0) {
			$.ajax({
				url : 'empListUpdate',
				contentType : 'application/json',
				// json객체를 문자열로 변환하여 전송
				data : JSON.stringify(empList) //JSON객체를 불러와서 Stringify()함수안에 배열
				,
				method : 'POST',
				dataType : 'json',
				success : function(response) {
					// 서버로부터의 응답을 콘솔에 출력
					console.log(response.updateResult);
				}

			});
			alert("Ajax empListUpdate 수행...");
		}
	}
</script>
</head>
<body>
	<h2>회원 정보3</h2>
	<table id="empList">
		<tr>
			<th>번호</th>
			<th>사번</th>
			<th>이름</th>
			<th>업무</th>
			<th>부서</th>
		</tr>
		<c:forEach var="emp" items="${listEmp}" varStatus="status">
			<tr id="empListRow">
				<td>emp${status.index}</td>

				<td><input type="hidden" class="deptno" id="deptno"
					name="deptno" value="${emp.deptno }"> <input type="text"
					class="empno" id="empno" name="empno" value="${emp.empno }">${emp.empno }</td>
				<td><input type="text" class="ename" id="ename" name="ename"
					value="${emp.ename }">${emp.ename }</td>
				<td>${emp.job }</td>
				<td>${emp.deptno }</td>
			</tr>

		</c:forEach>
	</table>
	RestController LISTVO3:
	<input type="button" id="btn_Dept3" value="empLISTTest 전송 "
		onclick="getEmpListUpdateTest()">
	<p>
		RestController LISTVO3: <input type="button" id="btn_Dept3"
			value="empLIST 전송 " onclick="getEmpListUpdate()">
	<p>
	<h1>The End</h1>
</body>
</html>