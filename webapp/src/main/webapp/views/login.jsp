<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="ListComputer"> Application -	Computer Database </a>
		</div>

	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="label.login" />
					</h1>

					<form name='login' action="login" method='POST'>
					<fieldset>
						<div class="form-group">
							<spring:message code="label.username" />
							<input type='text' name='username' value=''>
						</div>
						<div class="form-group">
							<spring:message code="label.password" />
							<input type='password' name='password' />
						</div>
						<div class = "pull-right">
						 	<input type="submit" value=<spring:message code="label.login" /> class="btn btn-primary">
						</div>
					</fieldset>
					
					<c:if test="${param.error=='true'}">
           				 Invalid username and password.
       				 </c:if>
						<c:if test="${param.logout =='true'}">
            			You have been logged out.
       				</c:if>
					</form>
				</div>
			</div>
		</div>
	</section>
<body>
</html>