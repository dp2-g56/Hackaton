<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PRISONER')">

	<h3>
		<spring:message code="prisoner.points" />
		:
		<jstl:out value="${prisoner.points}"></jstl:out>
	</h3>
	<br />

	<display:table pagesize="5" name="products" id="row"
		requestURI="${requestURI}">

		<display:column titleKey="product.name">
			<jstl:out value="${row.name}" />
		</display:column>
		<display:column titleKey="product.description">
			<jstl:out value="${row.description}" />
		</display:column>
		<display:column titleKey="product.type">
			<jstl:out value="${row.typeEN}" />
		</display:column>
		<display:column titleKey="product.price">
			<jstl:out value="${row.price}" />
		</display:column>
		<display:column titleKey="product.stock">
			<jstl:out value="${row.stock}" />
		</display:column>

	</display:table>

</security:authorize>