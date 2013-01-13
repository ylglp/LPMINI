<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Request Category Area</title>
</head>
<body>

<script type="text/javascript">

function checkinput() {
	var error = "";
	if (document.reqcatarea.categoryName.value == "")
		error += "Please enter the category name\n";
	if (document.reqcatarea.functionalAreaName.value == "")
		error += "Please enter the funcrtional area name\n";
	if (document.reqcatarea.description.value != "") {
		if (document.reqcatarea.description.value.length > 500)
			error += "Description cannt exceed 500 characters\n";
	}
	
	if (error == "") {
		return true;
	}
	else {
		alert(error);
		return false;
	}
}
</script>

	<H2>Add Request Category Area</H2><BR>
	
	<form:form name="reqcatarea" modelAttribute="reqCatArea" action="add" method="post" onsubmit="return checkinput();" >
		<table border="0" width="600" cellspacing="0" class="list">
			<tr>
				<td width="600" colspan="2"><form:errors path="id" htmlEscape="false" cssClass="lpfont_error" /></td>
			</tr>
			<tr>
				<td width="150">Category Name:</td>
				<td width="450">
					<form:input path="categoryName" size="30" maxlength="128"/><BR>
					<form:errors path="categoryName" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="150">Functional Area Name:</td>
				<td width="450">
					<form:input path="functionalAreaName" size="30" maxlength="128"/><BR>
					<form:errors path="functionalAreaName" htmlEscape="false" cssClass="lpfont_error" />
				</td>
			</tr>
			<tr>
				<td width="150">Description:</td>
				<td width="450">
					<form:textarea path="description" rows="3" cols="40" maxlength="500" style="width:230px;height:60px;"/>
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