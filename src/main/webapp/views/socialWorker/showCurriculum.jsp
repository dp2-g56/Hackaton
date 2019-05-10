<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<br/>

<spring:message code="personalRecord"	var="perRecord"/>
	<h4><jstl:out value="${perRecord}:"/></h4>

<table>
	<tr>
		<td><spring:message code="personalRecord.fullName" />:</td> 
		<td><jstl:out value="${curriculum.personalRecord.fullName}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="personalRecord.email"/>:</td> 
		<td><jstl:out value="${curriculum.personalRecord.email}" /> </td>
	</tr>
	<tr>
		<td><spring:message code="personalRecord.phoneNumber"/>:</td> 
		<td><jstl:out value="${curriculum.personalRecord.phoneNumber}" /> </td>
		
			<td>
				<spring:url var="editPersonalRecord" value="curriculum/socialWorker/editPersonalRecord.do">
					<spring:param name="personalRecordId" value="${curriculum.personalRecord.id}"/>
				</spring:url>	
				<a href="${editPersonalRecord}">
					<strong><spring:message code="button.edit" /></strong>
				</a>
		</td>
		
	</tr>
	<tr>
		<td><spring:message code="personalRecord.urlLinkedInProfile"/>:</td> 
		<td><a href="${curriculum.personalRecord.urlLinkedInProfile}" target="_blank" ><jstl:out value="${curriculum.personalRecord.urlLinkedInProfile}"/></a></td>
	</tr>
	<tr>
		<td><spring:message code="personalRecord.photo"/>:</td> 
		<td><img src="${curriculum.personalRecord.photo}" alt="${curriculum.personalRecord.photo}" style="width:100px;height:100px;border:0;"/> </td>
	</tr>

</table>

<spring:message code="educationRecord"	var="edRecord"/>
	<h4><jstl:out value="${edRecord}:"/></h4>

<display:table
	pagesize="${curriculum.educationRecords.size()}" name="${curriculum.educationRecords}" id="row"
	requestURI="${requestURI}">
	
	
	<display:column property="title" titleKey="educationRecord.title"/>
	<display:column property="institution" titleKey="educationRecord.institution"/>
	<display:column property="startDateStudy" titleKey="educationRecord.startDateStudy" format="{0,date,dd/MM/yyyy}"/>
	<display:column property="endDateStudy" titleKey="educationRecord.endDateStudy" format="{0,date,dd/MM/yyyy}"/>
	

	<display:column titleKey="educationRecord.attachment">
		<jstl:if test="${!row.link.isEmpty()}"><a href="${row.link}" target="_blank"><spring:message code="educationRecord.attachment" /></a></jstl:if>
	</display:column>
	
	<display:column>
			<spring:url var="editEducationRecord" value="curriculum/socialWorker/editEducationRecord.do">
				<spring:param name="educationRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editEducationRecord}">
				<strong><spring:message code="button.edit" /></strong>
			</a>	
	</display:column>

</display:table>

<spring:url var="addEducationRecord" value="curriculum/socialWorker/addEducationRecord.do"/>	
	<a href="${addEducationRecord}">
		<strong><spring:message code="socialWorker.addEducationRecord" /></strong>
	</a>	

<spring:message code="professionalRecord"	var="proRecord"/>
	<h4><jstl:out value="${proRecord}:"/></h4>

<display:table
	pagesize="${curriculum.professionalRecords.size()}" name="${curriculum.professionalRecords}" id="row"
	requestURI="${requestURI}">
	
	
	<display:column property="nameCompany" titleKey="professionalRecord.nameCompany"/>
	<display:column property="role" titleKey="professionalRecord.role"/>
	<display:column property="startDate" titleKey="educationRecord.startDateStudy" format="{0,date,dd/MM/yyyy}"/>
	<display:column property="endDate" titleKey="educationRecord.endDateStudy" format="{0,date,dd/MM/yyyy}"/>
	

	<display:column titleKey="educationRecord.attachment">
		<jstl:if test="${!row.linkAttachment.isEmpty()}"><a href="${row.linkAttachment}" target="_blank"><spring:message code="educationRecord.attachment" /></a></jstl:if>
	</display:column>
	
	<display:column>
			<spring:url var="editProfessionalRecord" value="curriculum/socialWorker/editProfessionalRecord.do">
				<spring:param name="professionalRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editProfessionalRecord}">
				<strong><spring:message code="button.edit" /></strong>
			</a>	
	</display:column>

</display:table>

<spring:url var="addProfessionalRecord" value="curriculum/socialWorker/addProfessionalRecord.do"/>	
	<a href="${addProfessionalRecord}">
		<strong><spring:message code="socialWorker.addProfessionalRecord" /></strong>
	</a>

<spring:message code="miscellaneousRecord"	var="misRecord"/>
	<h4><jstl:out value="${misRecord}:"/></h4>

<display:table
	pagesize="${curriculum.miscellaneousRecords.size()}" name="${curriculum.miscellaneousRecords}" id="row"
	requestURI="${requestURI}">
	
	
	<display:column property="title" titleKey="educationRecord.title"/>

	

	<display:column titleKey="educationRecord.attachment">
		<jstl:if test="${!row.linkAttachment.isEmpty()}"><a href="${row.linkAttachment}" target="_blank"><spring:message code="educationRecord.attachment" /></a></jstl:if>
	</display:column>
	
	<display:column>
			<spring:url var="editMiscellaneousRecord" value="curriculum/socialWorker/editMiscellaneousRecord.do">
				<spring:param name="miscellaneousRecordId" value="${row.id}"/>
			</spring:url>	
			<a href="${editMiscellaneousRecord}">
				<strong><spring:message code="button.edit" /></strong>
			</a>	
	</display:column>

</display:table>

<spring:url var="addMiscellaneousRecord" value="curriculum/socialWorker/addMiscellaneousRecord.do"/>	
	<a href="${addMiscellaneousRecord}">
		<strong><spring:message code="socialWorker.addMiscellaneousRecord" /></strong>
	</a>

