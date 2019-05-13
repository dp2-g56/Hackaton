<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="administrator.broadcast" />
</p>

<security:authorize access="hasRole('WARDEN')">

<form:form modelAttribute="messageSend" action="broadcast/warden/send.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<jstl:out value="${confirmation}" />	
	
	<acme:input code="broadcast.subject" path="subject"/>

	<acme:input code="broadcast.tags" path="tags"/>
	
	<acme:radio items="${priority}" itemsName="${priority}" code="broadcast.priority" path="priority"/>

	<acme:textarea code="broadcast.message" path="body"/>
	
	<br/>
	
	
	<acme:submit name="send" code="broadcast.send"/>
	
	<acme:submit name="sendGuards" code="broadcast.sendGuard"/>
	
	<acme:submit name="sendPrisoners" code="broadcast.sendPrisoners"/>
		
</form:form>

</security:authorize>