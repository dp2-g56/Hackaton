<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('VISITOR')">

	<form:form modelAttribute="finder"
		action="finder/visitor/edit.do">
		
		
		<form:hidden path="id" />
		
		<acme:textbox code="finder.keyWord" path="keyWord"/>

	
		<acme:selectStringWithNumber items="${values}" itemsName="${names}" number="${sizeOfList}" code="finder.charge" path="charge"/>

		
		<acme:submit name="save" code="finder.save"/>
		
		
	</form:form>
	
		<acme:cancel url="finder/visitor/list.do" code="finder.cancel"/>


</security:authorize>