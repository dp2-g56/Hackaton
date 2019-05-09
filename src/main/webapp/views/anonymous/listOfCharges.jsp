<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasAnyRole('WARDEN', 'VISITOR', 'GUARD', 'SALESMAN', 'SOCIALWORKER')">
			
	<display:table pagesize="5" name="charges" id="row" requestURI="${requestURI}" >
		
		<display:column titleKey="charge.title" >
				
			<jstl:set var="title" value="${row.titleEnglish}"/>
				<jstl:if test="${locale=='ES'}">
					<jstl:set var="title" value="${row.titleSpanish}"/>
				</jstl:if>
			
			<jstl:out value="${title}"/>
		</display:column>
		
		<display:column property="year" titleKey="charge.year" /> 
		<display:column property="month" titleKey="charge.month" /> 

	</display:table>
	
	<jstl:if test="${!warden}">
			<spring:url var="backUrl" value="/anonymous/prisoner/list.do">
			</spring:url>
			<a href="${backUrl}">
				<spring:message code="charge.back" />
			</a>
	</jstl:if>
	
	<jstl:if test="${warden}">
			<spring:url var="backUrl2" value="/warden/freePrisoners/list.do">
			</spring:url>
			<a href="${backUrl2}">
				<spring:message code="charge.back" />
			</a>
	</jstl:if>
	
	
</security:authorize>


<security:authorize access="isAnonymous()">
			
	<display:table pagesize="5" name="charges" id="row" requestURI="${requestURI}" >
		
		<display:column titleKey="charge.title" >
				
			<jstl:set var="title" value="${row.titleEnglish}"/>
				<jstl:if test="${locale=='ES'}">
					<jstl:set var="title" value="${row.titleSpanish}"/>
				</jstl:if>
			
			<jstl:out value="${title}"/>
		</display:column>
		
		<display:column property="year" titleKey="charge.year" /> 
		<display:column property="month" titleKey="charge.month" /> 

	</display:table>
	
	
	
			<spring:url var="backUrl" value="/anonymous/prisoner/list.do">
			</spring:url>
			<a href="${backUrl}">
				<spring:message code="charge.back" />
			</a>
	
	
	
</security:authorize>
