<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PRISONER')">

	<form:form modelAttribute="finderActivities"
		action="finderActivities/prisoner/edit.do">
		
		
		<form:hidden path="id" />
		
		<acme:textbox code="finder.keyWord" path="keyWord"/>

	
		<acme:datebox code="finder.maxDate" path="maxDate"/>
		
		<acme:datebox code="finder.minDate" path="minDate"/>

		
		<acme:submit name="save" code="finder.save"/>
		
		
	</form:form>
	
		<acme:cancel url="finderActivities/prisoner/list.do" code="finder.cancel"/>


</security:authorize>