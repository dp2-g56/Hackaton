<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PRISONER')">
			
	<display:table pagesize="5" name="visitors" id="row" requestURI="${requestURI}" >
		
		<display:column property="name" titleKey="visitor.name" /> 
		<display:column property="middleName" titleKey="visitor.middleName" /> 
		<display:column property="surname" titleKey="visitor.surname" /> 
		<display:column titleKey="visitor.photo">
			<a href="${row.photo}">
				<spring:message code="visitor.viewPhoto"/>
			</a>
		</display:column> 
			
		
		
		<display:column>
		    <spring:url var="createVisitUrl" value="/visit/prisoner/create.do?visitorId={visitorId}">
		    	<spring:param name="visitorId" value="${row.id}" />
		    </spring:url>
		        	
		   	<a href="${createVisitUrl}">
		     	<spring:message var ="createVisit" code="visit.CreateVisit" />
		       	<jstl:out value="${createVisit}" />   
			</a>
		</display:column>

        
	</display:table>
	
</security:authorize>
