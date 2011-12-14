<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jes.museumtab.backend.ExhibitData" %>
<%@ page import="com.jes.museumtab.backend.Dao" %>

<!DOCTYPE html>

<%@page import="java.util.ArrayList" %>

<html>
	<head>
		<title>Exhibits</title>
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<meta charset="utf-8">
	</head>
	<body>
<%
Dao dao = Dao.INSTANCE;

List<ExhibitData> exhibits = dao.listExhibits();

%>

Exhibits:

<table>
<tr>
<th>Name</th>
<th>Description</th>
</tr>

<% for (ExhibitData exhibit : exhibits) {%>
<tr>
<td>
<%=exhibit.getName()%>
</td>
<td>
<%=exhibit.getDescription()%>
</td>
<tr>
<%}
%>
</table>

<hr />

<div class="main">

<div class="headline">New exhibit</div>

<form action="/new" method="post" accept-charset="utf-8">
	<table>
		<tr>
			<td><label for="name">Name</label></td>
			<td><input type="text" name="name" id="name" size="65"/></td>
		</tr>
		<tr>
			<td valign="desc"><label for="desc">Description</label></td>
			<td><textarea rows="4" cols="50" name="desc" id="desc"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit" value="Create"/></td>
		</tr>		
	</table>
</form>
</div>
</body>
</html>
