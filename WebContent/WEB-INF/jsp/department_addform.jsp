<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Department</title>
</head>
<body>

<script type="text/javascript">

function checkinput() {
	var error = "";
	var id = 0;
	if (document.department.name.value == "")
		error += "Please enter the department name\n";
	if (document.department.description.value != "") {
		if (document.department.description.value.length > 500)
			error += "Description cannt exceed 500 characters\n";
	}
	id = parseInt(document.department.deptHead.value, 10);
	if (id <= 0)
		error += "Please enter positive department head user id\n";
	id = parseInt(document.department.parentDeptId.value, 10);
	if (id <= 0)
		error += "Please enter positive parent department id\n";
	id = parseInt(document.department.ownerAccountId.value, 10);
	if (id <= 0)
		error += "Please enter positive owner account id\n";
	
	if (error == "") {
		return true;
	}
	else {
		alert(error);
		return false;
	}
}
</script>

	<H2>Add Department</H2><BR>
	
	<form:form name="department" modelAttribute="department" action="add" method="post" onsubmit="return checkinput();" >
		<table border="0" width="600" cellspacing="0" class="list">
			<tr>
				<td width="600" colspan="2"><form:errors path="id" htmlEscape="false" cssClass="lpfont_error" /></td>
			</tr>
			<tr>
				<td width="150">Name:</td>
				<td width="450">
					<form:input path="name" size="30" maxlength="128"/><BR>
					<form:errors path="name" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="150">Description:</td>
				<td width="450">
					<form:textarea path="description" rows="3" cols="40" maxlength="500" style="width:230px;height:60px;"/>
				</td>
			</tr>
			<tr>
				<td width="150">Department Head Id:</td>
				<td width="450">
					<form:input path="deptHead" size="30" maxlength="128"/><BR>
					<form:errors path="deptHead" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="150">Parent Department Id:</td>
				<td width="450">
					<form:input path="parentDeptId" size="30" maxlength="128"/><BR>
					<form:errors path="parentDeptId" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="150">Owner Account Id:</td>
				<td width="450">
					<form:input path="ownerAccountId" size="30" maxlength="128"/><BR>
					<form:errors path="ownerAccountId" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="600" colspan="2"><button type="submit" class="lp_button">Save</button>&nbsp;</td>
			</tr>
				
		</table>	
	</form:form>
	
	<BR><BR>
	<a href="<c:url value="/index.jsp"/>">LPMINI Home</a><BR>
	
</body>
</html>
