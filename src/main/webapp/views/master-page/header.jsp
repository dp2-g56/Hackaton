<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('WARDEN')">
			<li><a class="fNiv"><spring:message
						code="master.page.warden" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="warden/freePrisoners/list.do"><spring:message code="master.page.freePrisoners" /></a></li>
					<li><a href="report/warden/list.do"><spring:message code="master.page.report" /></a></li>
					<li><a href="warden/warden/register.do"><spring:message code="master.page.warden.register" /></a></li>
					<li><a href="prisoner/warden/register.do"><spring:message code="master.page.prisoner.register" /></a></li>
					<li><a href="salesman/warden/register.do"><spring:message code="master.page.salesman.register" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('PRISONER')">
			<li><a class="fNiv"><spring:message
						code="master.page.prisoner" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="visit/prisoner/list.do"><spring:message code="master.page.listVisits" /></a></li>
					<li><a href="product/salesman/prisoner/list.do"><spring:message code="master.page.salesMen" /></a></li>
					<li><a href="finderActivities/prisoner/list.do"><spring:message code="master.page.finderActivities" /></a></li>
				</ul></li>
		</security:authorize>
		
			<security:authorize access="hasRole('VISITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.visitor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="visit/visitor/list.do"><spring:message code="master.page.listVisits" /></a></li>
					<li><a href="finder/visitor/list.do"><spring:message code="master.page.finderVisitor" /></a></li>	
				</ul></li>
		</security:authorize>
		
		<security:authorize access="hasRole('GUARD')">
			<li><a class="fNiv"><spring:message
						code="master.page.guard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="visit/guard/listFuture.do"><spring:message code="master.page.assignableVisits" /></a></li>
					<li><a href="visit/guard/list.do"><spring:message code="master.page.listVisits" /></a></li>
				</ul></li>
		</security:authorize>
		
		<security:authorize access="hasRole('SOCIALWORKER')">
			<li><a class="fNiv"><spring:message
						code="master.page.socialWorker" /></a>
				<ul>
					<li class="arrow"></li>
					
				</ul></li>
		</security:authorize>
		
		<security:authorize access="hasRole('SALESMAN')">
			<li><a class="fNiv"><spring:message
						code="master.page.salesMan" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="product/salesman/list.do"><spring:message code="master.page.products" /></a></li>
					
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasAnyRole('VISITOR', 'WARDEN', 'GUARD', 'SALESMAN', 'SOCIALWORKER')">
		<li><a href="anonymous/prisoner/list.do"><spring:message code="master.page.listOfPrisoners" /> </a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('PRISONER')">
		<li><a href="visitor/prisoner/list.do"><spring:message code="master.page.myVisitors" /> </a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
		<li><a href="anonymous/prisoner/list.do"><spring:message code="master.page.listOfPrisoners" /> </a></li>
		</security:authorize>
		
		<security:authorize access="permitAll">
		<li><a href="product/anonymous/list.do"><spring:message code="master.page.listOfProducts" /> </a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="authenticated/showProfile.do"><spring:message code="master.page.myProfile" /> </a></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.mailSystem" /> </a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

