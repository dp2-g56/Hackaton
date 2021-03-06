<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('WARDEN')">

<form:form modelAttribute="formPrisoner" action="prisoner/warden/register.do">

	<!-- ELECCI�N DEL FORMATO DE LA FECHA E IDIOMA CARGOS -->
	<jstl:if test="${locale =='EN'}">
		<jstl:set var="url" value ="anonymous/termsAndConditionsEN.do"/>	
		<jstl:set var="chargesLanguage" value ="titleEnglish"/>	
	</jstl:if>
	
	<jstl:if test="${locale =='ES'}">
		<jstl:set var="url" value ="anonymous/termsAndConditionsES.do"/>
		<jstl:set var="chargesLanguage" value ="titleSpanish"/>	
	</jstl:if>
	

	<!-- User Account Attributes -->
	<fieldset>
    	<legend><spring:message code="warden.userAccountData" /></legend>
	
		<acme:textbox path="username" code="warden.username" />
		<br />
		
		<acme:password path="password" code="warden.password" />
		<br />
		
		<acme:password path="confirmPassword" code="warden.confirmPassword" />
		<br />
	
	</fieldset>
	<br />
	
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
	
	<!-- Prisoner Attributes -->
	<fieldset>
    	<legend><spring:message code="prisoner.charges.select" /></legend>
		
		<acme:selectMandatory path="charges" code="prisoner.charges" items="${finalCharges}" itemLabel="${chargesLanguage}"/>
		<br />
	</fieldset>
	<br />
	
	<!-- TERMS AND CONDITIONS -->
	<fieldset>
    	<legend><spring:message code="warden.termsAndConditions" /></legend>
    
    <form:checkbox path="termsAndConditions" /> 
		<spring:message code="warden.acceptTemsConditions" />
			<a href="${url}" target="_blank"> 
					<spring:message code="warden.termsAndConditions" /> </a>
					<form:errors path="termsAndConditions" cssClass="error" />
	<br />
	</fieldset>
	<br />

	<!-- BOTONES -->	
	<input type="submit" name="save" value="<spring:message code="warden.save" />"/> 
	<acme:cancel url="/" code="warden.cancel" /> 
	
	</form:form>
	
	
	
</security:authorize>