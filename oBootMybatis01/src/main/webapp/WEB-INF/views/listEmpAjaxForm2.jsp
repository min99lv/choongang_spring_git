<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	 function getListDept(){
		 alert("getListDept Run...");
		 var str = "";
		 var str2 = "";
		 
		 $.ajax(
			{
				url:"/sendVO3",
				dataType:'json',
				success : function(deptList){
					var jsonStr = JSON.stringify(deptList);
					alert("jsonStr-->"+jsonStr);	
					$('#dept_list_str').append(jsonStr);
					str += "<select name='dept'>";
					$(deptList).each(
						function(){
							str2 = "<option value='"+this.deptno+"'>"+this.dname+"</option>";
							str += str2;
						}
						)
						str += "</select><p>"
						alert("combobox str->" +str);
						$('#dept_list_combobox').append(str);
					
				}
			}	 
		 );
	 }
	 
	 
	 function getEmpnoDelete(pIndex) {
	        // url    -> empnoDelete
	        // parm   -> empno : selEmpno 
	        // 성공하면  ->  Delete Tag
	        var selEmpno  = $("#empno"+pIndex).val();
	        var selEname  = $("#ename"+pIndex).val();
           alert("selEmpno->"+selEmpno);
	       
           // 결과를 text로 받음
           /*  $.ajax(
	            {
	                url:"/empnoDelete",
	                data:{empno : selEmpno,
	                      ename : selEname},
	                dataType:'text', //서버가 반환하는 객체 타입
	                success: function(data) {
	                    alert(".ajax getDeptDelete data->"+data);
	                    if(data == '1') {
	                        // 성공하면 아래라인 수행
	                        $('#emp'+pIndex).remove();         // Delete Tag
	                    }
	                }
	            }
	        ); */
	        
	        // 결과를 객체로 받음
	       $.ajax(
	            {
	                url:"/empnoDelete03",
	                data:{empno : selEmpno,
	                      ename : selEnameqh},
	                dataType:'json', //서버가 반환하는 객체 타입
	                success: function(response) {
	                    alert(".ajax getDeptDelete response.delStatus->"+response.delStatus);
	                    if(response.delStatus == '1') {
	                        // 성공하면 아래라인 수행
	                        $('#emp'+pIndex).remove();         /* Delete Tag */
	                    }
	                }
	            }
	        ); 
	    }
</script>
<body>
	<h2>회원 정보</h2>
	<table>
		<tr>
			<th>번호</th>
			<th>사번</th>
			<th>이름</th>
			<th>업무</th>
			<th>부서</th>
		</tr>
		<c:forEach var="emp" items="${listEmp}" varStatus="status">
			<tr id="emp${status.index}">
				<td>emp${status.index}</td>
				<td><input type="hidden" id="deptno${status.index}"
					value="${emp.deptno }"> <input type="text"
					id="empno${status.index}" value="${emp.empno}"></td>
				<td><input type="text" id="ename${status.index}"
					value="${emp.ename}"></td>
				<td>${emp.job}</td>
				<td>${emp.deptno}<input type="button" id="btn_idCheck2"
					value="사원 Row Delete" onclick="getEmpnoDelete(${status.index})">
				</td>
			</tr>
		</c:forEach>
	</table>
	RestController LISTVO3:
	<input type="button" id="btn_Dept3" value="부서명 LIST"
		onclick="getListDept()">
	<p>dept_list_str:
	<div id="dept_list_str"></div>
	dept_list_combobox:
	<div id="dept_list_combobox"></div>
	<h1>The End</h1>
</body>
</html>