<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"  tagdir="/WEB-INF/tags"%>

<security:authorize access="permitAll">


	<jstl:if test="${prisoner}">

			<h3> <spring:message code="product.currentPoints"/> 
			<div style="color:red; display:inline;"> <jstl:out value=" ${points} " /> </div>  
			<spring:message code="product.points"/> </h3>
			
	</jstl:if>
			
	<display:table pagesize="5" name="products" id="row" requestURI="${requestURI}" >
		
		<display:column property="name" titleKey="product.name" /> 
		<display:column property="description" titleKey="product.description" /> 
		<display:column property="type" titleKey="product.type" /> 
		<display:column property="price" titleKey="product.price" /> 
		<display:column property="stock" titleKey="product.stock" /> 
		
		<display:column>
				<jstl:if test="${prisoner}">
					<spring:url var="purchaseUrl" value="/product/prisoner/buy.do?productId={productId}">
							<spring:param name="productId" value="${row.id}" />
					</spring:url>
							<a href="${purchaseUrl}">
								<spring:message code="product.purchase" />
							</a>
				</jstl:if>
		</display:column>
		
	</display:table>
	
	<jstl:if test="${prisoner}">
			<spring:url var="backUrl" value="/product/salesman/prisoner/list.do">
			</spring:url>
					<a href="${backUrl}">
						<spring:message code="product.back" />
					</a>
	</jstl:if>
</security:authorize>
