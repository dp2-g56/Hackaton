<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('WARDEN')">

<display:table pagesize="5" name="configuration" id="row" class="displaytag" 
					requestURI="configuration/warden/list.do">
					
	<display:column property="finderResult" titleKey="configuration.finderResult" /> 
					
	<display:column property="minFinderResults" titleKey="configuration.minFinderResults" />
	
	<display:column property="maxFinderResults" titleKey="configuration.maxFinderResults" />
	
	<display:column property="timeFinderPrisoners" titleKey="configuration.timeFinderPrisoner" /> 
	
	<display:column property="timeFinderActivities" titleKey="configuration.timeFinderActivities" /> 
					
	<display:column property="spainTelephoneCode" titleKey="configuration.spainTelephoneCode" />

	<display:column property="welcomeMessageEnglish" titleKey="configuration.welcomeMessageEnglish" />
	
	<display:column property="welcomeMessageSpanish" titleKey="configuration.welcomeMessageSpanish" />
	
	<display:column property="systemName" titleKey="configuration.systemName" />
	
	<display:column property="imageURL" titleKey="configuration.imageURL" />
	
</display:table>

<input type="button" onclick="javascript:relativeRedir('configuration/warden/edit.do');"  value="<spring:message code="configuration.edit.button"/>" />	
<input type="button" onclick="javascript:relativeRedir('configuration/warden/listSpam.do');"  value="<spring:message code="configuration.spam.button"/>" />	
<input type="button" onclick="javascript:relativeRedir('configuration/warden/listTypeProd.do');"  value="<spring:message code="configuration.typeProd.button"/>" />

</security:authorize>
