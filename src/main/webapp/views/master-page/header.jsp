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
	<a href="#"><img src="${imageURL}" height= 150px width= 500px alt="Acme Alcatraz Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('WARDEN')">
			<li><a class="fNiv"><spring:message
						code="master.page.warden" /></a>
				<ul>
					<li class="arrow"></li>
					
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('PRISONER')">
			<li><a class="fNiv"><spring:message
						code="master.page.prisoner" /></a>
				<ul>
					<li class="arrow"></li>
					
				</ul></li>
		</security:authorize>
		
			<security:authorize access="hasRole('VISITOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.visitor" /></a>
				<ul>
					<li class="arrow"></li>
					
				</ul></li>
		</security:authorize>
		
		<security:authorize access="hasRole('GUARD')">
			<li><a class="fNiv"><spring:message
						code="master.page.guard" /></a>
				<ul>
					<li class="arrow"></li>
					
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
					
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

