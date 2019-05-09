<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<br/>
	<!-- Actor Data -->
	<table>
		<tr>
			<td><spring:message code="actor.fullName" />:</td>
			<td><jstl:out
					value="${actor.name} ${actor.middleName} ${actor.surname}" /></td>
		</tr>
		<tr>
			<td><spring:message code="actor.photo" />:</td>
			<td><jstl:out value="${actor.photo}" /></td>
		</tr>
	<jstl:choose>
		<jstl:when test="${curriculum != null}">
			<tr>
				<td><spring:message code="actor.curriculum" />:</td>
				<td><spring:url var="showCurriculumUrl" value="curriculum/socialWorker/show.do"/>	
					<a href="${showCurriculumUrl}">
						<strong><spring:message code="actor.showCurriculum" /></strong>
					</a>			
				</td>
			</tr>
		</jstl:when><jstl:otherwise>
			<tr>
				<td><spring:message code="actor.curriculum" />:</td>
				<td><spring:url var="showCurriculumUrl" value="curriculum/socialWorker/register.do"/>	
					<a href="${showCurriculumUrl}">
						<strong><spring:message code="actor.registerCurriculum" /></strong>
					</a>			
				</td>
			</tr>
		
		</jstl:otherwise>
	</jstl:choose>

	</table>

