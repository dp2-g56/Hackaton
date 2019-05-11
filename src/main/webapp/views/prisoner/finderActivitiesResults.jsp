<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<%@ page import="java.util.Map" %>

<security:authorize access="hasRole('PRISONER')">

	<a href= "finderActivities/prisoner/edit.do"><button><spring:message code="visitor.editFinder"/></button> </a>

			
	<display:table pagesize="5" name="activities" id="row" requestURI="${requestURI}" >
	
		
	<jstl:choose>
	
	<jstl:when test="${map[row] != row.maxAssistant}">
		<jstl:set var="color" value="black"/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="color" value="red"/>
	</jstl:otherwise>
	
	</jstl:choose>
	
	<display:column property="title" titleKey="activity.title" /> 
	<display:column property="description" titleKey="activity.description" /> 
	<display:column property="realizationDate" titleKey="activity.realizationTime" /> 
	<display:column titleKey="activity.maxAssistant" style="color:${color}"> 

	<jstl:out value="${map[row]} / ${row.maxAssistant}"/>
	
	</display:column>
	<display:column property="rewardPoints" titleKey="activity.rewardPoints" /> 
	<display:column titleKey="activity.request">
	
	
	</display:column>
				
		
	</display:table>
		
		
</security:authorize>