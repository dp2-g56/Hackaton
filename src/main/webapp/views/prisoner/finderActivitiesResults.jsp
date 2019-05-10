<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PRISONER')">

	<a href= "finderActivities/prisoner/edit.do"><button><spring:message code="visitor.editFinder"/></button> </a>
			
	<display:table pagesize="5" name="activities" id="row" requestURI="${requestURI}" >
	
	<display:column property="title" titleKey="activity.title" /> 
	<display:column property="description" titleKey="activity.description" /> 
	<display:column property="realizationDate" titleKey="activity.realizationTime" /> 
	<display:column property="maxAssistant" titleKey="activity.maxAssistant" /> 
	<display:column property="rewardPoints" titleKey="activity.rewardPoints" /> 
	<display:column titleKey="activity.request">
	
	
	</display:column>
				
		
	</display:table>
		
		
</security:authorize>