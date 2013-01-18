<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LPMINI - List All RequestCategoryArea</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/main.css"/>" />

</head>
<body>
	LPMINI - List All Departments<BR><BR>
	
	<c:choose>
		<c:when test="${empty departments}">
			No Department was found<BR>
		</c:when>
		<c:otherwise>
			Found Departments: ${fn:length(departments)}<BR><BR>
			<table border="1" width="900" cellspacing="0" class="list">
				<tr>
					<th width="50" align="left">Id</th>
					<th width="200" align="left">Name</th>
					<th width="250" align="left">Description</th>
					<th width="100" align="left">DeptHead</th>
					<th width="125" align="left">ParentDeptId</th>
					<th width="150" align="left">OwnerAccountId</th>
				</tr>
				<c:forEach items="${departments}" var="dept">
				<tr>
					<td width="50" align="left">${dept.id}</td>
					<td width="200" align="left">${dept.name}</td>
					<td width="250" align="left">${dept.description}</td>
					<td width="100" align="left">${dept.deptHead}</td>
					<td width="125" align="left">${dept.parentDeptId}</td>
					<td width="150" align="left">${dept.ownerAccountId}</td>
				</tr>
				</c:forEach>
			</table>			
		</c:otherwise>
	</c:choose>

	<BR>
	<form name="development_del" action="del" method="POST">
		Enter Department Id to delete: <input type="text" name="id" size="10">&nbsp;&nbsp;
		<button type="submit" class="lp_button">Delete</button>
	</form>
	
	<form name="development_edit" action="editform" method="POST">
		Enter Department Id to edit: <input type="text" name="id" size="10">&nbsp;&nbsp;
		<button type="submit" class="lp_button">Edit</button>
	</form>
	
	<BR>
	<a href="<c:url value="/lpac/request/department/addform"/>">Add Department</a><BR>
	
	<BR><BR>
	<a href="<c:url value="/index.jsp"/>">LPMINI Home</a><BR>
	
</body>
</html>
