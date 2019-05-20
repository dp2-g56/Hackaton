<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>


<p>
	<spring:message code="administrator.allCharges" />
</p>

<security:authorize access="hasRole('WARDEN')">

	<display:table pagesize="5" name="draftCharges" id="row" requestURI="${requestURI}" >
		
		<display:column titleKey="charge.title" >
				
			<jstl:set var="title" value="${row.titleEnglish}"/>
				<jstl:if test="${locale=='ES'}">
					<jstl:set var="title" value="${row.titleSpanish}"/>
				</jstl:if>
			<jstl:out value="${title}"/>
		</display:column>
		
		<display:column titleKey="charge.year" > 
			<jstl:out value="${row.year} years and ${row.month} months " />
		</display:column>
		
		<display:column titleKey="charge.month" > 
			<jstl:out value="${row.month}" />
		</display:column>
		
		<display:column titleKey="charge.isDraftMode" > 
			<jstl:out value="${row.isDraftMode}" />
		</display:column>
		
		<display:column>
				<jstl:if test="${row.isDraftMode}">
				
					<spring:url var="newCharge" value="/warden/charge/editCharge.do?chargeId=${row.id}">
					
					</spring:url>
			
					<a href="${newCharge}">
						<spring:message code="warden.edit"/>
					</a>
				
				
					
				</jstl:if>
		</display:column>
		
		<display:column>
				<jstl:if test="${row.isDraftMode}">
				
					<spring:url var="newCharge" value="/warden/charge/deleteCharge.do?chargeId=${row.id}">
					
					</spring:url>
			
					<a href="${newCharge}" onclick="return confirm('<spring:message code="mail.delete" />')">
						<spring:message code="warden.delete"/>
					</a>
				
				
					
				</jstl:if>
		</display:column>

	</display:table>
	
	<display:table pagesize="5" name="finalCharges" id="rowa" requestURI="${requestURI}" >
		
		<display:column titleKey="charge.title" >
				
			<jstl:set var="title" value="${rowa.titleEnglish}"/>
				<jstl:if test="${locale=='ES'}">
					<jstl:set var="title" value="${rowa.titleSpanish}"/>
				</jstl:if>
			<jstl:out value="${title}"/>
		</display:column>
		
		<display:column titleKey="charge.year" > 
			<jstl:out value="${rowa.year} years and ${rowa.month} months " />
		</display:column>
		
		<display:column titleKey="charge.month" > 
			<jstl:out value="${rowa.month}" />
		</display:column>
		
		<display:column titleKey="charge.isDraftMode" > 
			<jstl:out value="${rowa.isDraftMode}" />
		</display:column>

	</display:table>
	
	
	<spring:url var="newCharge" value="/warden/charge/newCharge.do"/>
	<p><a href="${newCharge}"><spring:message code="warden.newCharge" /></a></p>
	
	
</security:authorize>
