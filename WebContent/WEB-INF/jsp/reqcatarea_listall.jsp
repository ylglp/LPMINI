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
	LPMINI - List All RequestCategoryArea<BR><BR>
	
	<c:choose>
		<c:when test="${empty requestCategoryAreas}">
			No RequestCategoryArea was found<BR>
		</c:when>
		<c:otherwise>
			Found RequestCategoryArea: ${fn:length(requestCategoryAreas)}<BR><BR>
			<table border="1" width="600" cellspacing="0" class="list">
				<tr>
					<th width="100" align="left">Id</th>
					<th width="150" align="left">Category</th>
					<th width="150" align="left">Functional Area</th>
					<th width="200" align="left">Description</th>
				</tr>
				<c:forEach items="${requestCategoryAreas}" var="reqcatarea">
				<tr>
					<td width="100" align="left">${reqcatarea.id}</td>
					<td width="150" align="left">${reqcatarea.categoryName}</td>
					<td width="150" align="left">${reqcatarea.functionalAreaName}</td>
					<td width="200" align="left">${reqcatarea.description}</td>
				</tr>
				</c:forEach>
			</table>			
		</c:otherwise>
	</c:choose>
	
	<form name="reqcatarea_del" action="del" method="POST">
		Enter RequestCategoryArea Id to delete: <input type="text" name="id" size="10">&nbsp;&nbsp;
		<button type="submit" class="lp_button">Delete</button>
	</form>
	
	<form name="reqcatarea_edit" action="editform" method="POST">
		Enter RequestCategoryArea Id to edit: <input type="text" name="id" size="10">&nbsp;&nbsp;
		<button type="submit" class="lp_button">Edit</button>
	</form>
	
	<BR>
	<a href="<c:url value="/lpac/request/catarea/addform"/>">Add Request Category Area</a><BR>
	
	<BR><BR>
	<a href="<c:url value="/index.jsp"/>">LPMINI Home</a><BR>
	
</body>
</html>