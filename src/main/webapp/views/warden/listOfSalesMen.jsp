<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('WARDEN')">
			
	<display:table pagesize="5" name="salesmen" id="row" requestURI="${requestURI}" >
		
		<display:column property="name" titleKey="salesman.name" />
		<display:column property="middleName" titleKey="salesman.middleName" />
		<display:column property="surname" titleKey="salesman.surname" />
		
		<display:column titleKey="salesman.photo">
			<a href="${row.photo}">
				<spring:message code="salesman.viewPhoto"/>
			</a>
		</display:column> 
		
		<display:column property="VATNumber" titleKey="salesman.VATNumber" /> 
		
		<display:column property="storeName" titleKey="salesman.storeName" /> 
        
		<display:column titleKey="actor.profiles">
			<spring:url var="salesmanUrl"
				value="/salesman/warden/edit.do">
				<spring:param name="salesmanId" value="${row.id}" />
			</spring:url>
			<a href="${salesmanUrl}"><spring:message code="actor.editProfile" /></a>
		</display:column>
        
	</display:table>
	
</security:authorize>
