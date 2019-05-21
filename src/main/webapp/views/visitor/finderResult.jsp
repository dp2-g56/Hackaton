<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('VISITOR')">

	<a href= "finder/visitor/edit.do"><button><spring:message code="visitor.editFinder"/></button> </a>

			
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

		<display:column titleKey="prisoner.isIsolated">
	
					<jstl:if test="${row.isIsolated}">
						<jstl:set var="isIsolated" value="YES"/>
						<jstl:if test="${locale=='ES'}">
							<jstl:set var="isIsolated" value="SI"/>
						</jstl:if>
					</jstl:if>
					<jstl:if test="${!row.isIsolated}">
						<jstl:set var="isIsolated" value="NO"/>
						<jstl:if test="${locale=='ES'}">
							<jstl:set var="isIsolated" value="NO"/>
						</jstl:if>
					</jstl:if>

					<jstl:out value="${isIsolated}"/>
	
		</display:column>
		
		<display:column titleKey="prisoner.freedom">
	
					<jstl:if test="${row.freedom}">
						<jstl:set var="freedom" value="YES"/>
						<jstl:if test="${locale=='ES'}">
							<jstl:set var="freedom" value="SI"/>
						</jstl:if>
					</jstl:if>
					<jstl:if test="${!row.freedom}">
						<jstl:set var="freedom" value="NO"/>
						<jstl:if test="${locale=='ES'}">
							<jstl:set var="freedom" value="NO"/>
						</jstl:if>
					</jstl:if>

					<jstl:out value="${freedom}"/>
	
		</display:column>
				
		<display:column  titleKey="prisoner.charges" > 
			
			<jstl:if test="${!warden}">
				<spring:url var="chargesUrl"
					value="/finder/visitor/charges.do?prisonerId={prisonerId}">
					<spring:param name="prisonerId" value="${row.id}" />
				</spring:url>
				<a href="${chargesUrl}"><spring:message code="prisoner.viewCharges" /></a>
			</jstl:if>
			
			<jstl:if test="${warden}">
				<spring:url var="chargesUrl"
					value="/warden/charge/list.do?prisonerId={prisonerId}">
					<spring:param name="prisonerId" value="${row.id}" />
				</spring:url>
				<a href="${chargesUrl}"><spring:message code="prisoner.viewCharges" /></a>
			</jstl:if>
		</display:column>
		
		
		<!-- BOTON DE CREATE VISIT -->
		
				<display:column>
					<jstl:if test="${(!row.freedom) && (!row.isIsolated)}">
		       		<spring:url var="createVisitUrl" value="/finder/visitor/createVisit.do?prisonerId={prisonerId}">
		            	<spring:param name="prisonerId" value="${row.id}"/>
		            	
		        	</spring:url>
		        	
		        	<a href="${createVisitUrl}">
		              <spring:message var ="createVisit" code="visit.CreateVisit" />
		             <jstl:out value="${createVisit}" />   
		        	</a>
		        	</jstl:if>
		        </display:column>
		        
 
        
	</display:table>



</security:authorize>