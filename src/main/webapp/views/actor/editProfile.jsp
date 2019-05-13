<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasAnyRole('WARDEN')">
	<jstl:if test="${warden!=null}">
		<form:form modelAttribute="warden" action="authenticated/editProfile.do">
		
		<form:hidden path = "id"/>
		
		<!-- Actor Attributes -->
		<fieldset>
	    	<legend><spring:message code="warden.personalData" /></legend>
			
			<acme:textbox path="name" code="warden.name" />
			<br />
			
			<acme:textbox path="middleName" code="warden.middleName" />
			<br />
			
			<acme:textbox path="surname" code="warden.surname" />
			<br />
			
			<acme:textbox path="photo" code="warden.photo" />
			<br />
		</fieldset>
		<br />
		
		<!-- BOTONES -->	
		<input type="submit" name="saveWarden" value="<spring:message code="warden.edit" />"/> 
		<acme:cancel url="/authenticated/showProfile.do" code="warden.cancel" /> 
		
		</form:form>
	</jstl:if>
	
</security:authorize>