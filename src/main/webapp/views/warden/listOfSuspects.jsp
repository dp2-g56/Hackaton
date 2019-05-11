<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('WARDEN')">
			
	<display:table pagesize="5" name="prisoners" id="row" requestURI="${requestURI}" >
		
		<display:column property="name" titleKey="prisoner.name" /> 
		<display:column property="middleName" titleKey="prisoner.middleName" /> 
		<display:column property="surname" titleKey="prisoner.surname" /> 
		<display:column titleKey="prisoner.photo">
			<a href="${row.photo}">
				<spring:message code="prisoner.viewPhoto"/>
			</a>
		</display:column> 
			
		<display:column property="crimeRate" titleKey="prisoner.crimeRate" /> 
		<display:column property="ticker" titleKey="prisoner.ticker" /> 		
		<display:column property="incomeDate" titleKey="prisoner.incomeDate" /> 
		<display:column property="exitDate" titleKey="prisoner.exitDate" /> 

				
		<display:column  titleKey="prisoner.charges" > 

				<spring:url var="chargesUrl"
					value="/prisoner/warden/listSuspectCharges.do?prisonerId={prisonerId}">
					<spring:param name="prisonerId" value="${row.id}" />
				</spring:url>
				<a href="${chargesUrl}"><spring:message code="prisoner.viewCharges" /></a>

		</display:column>
		
		<display:column  > 

				<spring:url var="chargesUrl"
					value="/prisoner/warden/isolate.do?prisonerId={prisonerId}">
					<spring:param name="prisonerId" value="${row.id}" />
				</spring:url>
				<a href="${chargesUrl}"><spring:message code="prisoner.isolate" /></a>

		</display:column>
		
        
	</display:table>
	
</security:authorize>
