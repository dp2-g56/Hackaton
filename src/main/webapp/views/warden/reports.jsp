<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('WARDEN')">
			
	<display:table pagesize="5" name="reports" id="row" requestURI="${requestURI}" >
		
		<display:column property="description" titleKey="report.description" /> 
		<display:column property="date" titleKey="report.date" /> 
		<display:column titleKey="report.visit.prisoner">
			<spring:url var="prisonerUrl" value="/warden/prisoner/show.do">
				<spring:param name="prisonerId" value="${reportsAndPrisoner.get(row).id}" />
			</spring:url>
			<a href="${prisonerUrl}">
				<jstl:out value="${reportsAndPrisoner.get(row).userAccount.username}"/>
			</a>
		</display:column>
			    
	</display:table>
	
</security:authorize>