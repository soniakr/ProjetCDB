<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ListComputers"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${idComputer}"></c:out>
                    </div>
                    <h1><spring:message code="editButton"/></h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" id="id" name="id" value="${computerToUpdate.idComputer }"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computerName"/></label>
                                <input type="text" class="form-control" id="name" name="name" value="${computerToUpdate.name}" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${computerToUpdate.introduced}" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${computerToUpdate.discontinued}" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="companyName"/></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <c:forEach items="${companies}" var="company">
	                                	<c:if test="${company.idCompany==computerToUpdate.company.idCompany}">
	                                		 <option value="${company.idCompany}" selected><c:out value="${company.nameCompany}"></c:out></option>
	                                	</c:if>
	                                	<c:if test="${company.idCompany!=computerToUpdate.company.idCompany}">
  	                                   		 <option value="${company.idCompany}"><c:out value="${company.nameCompany}"></c:out></option>
										</c:if>
									</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="ListComputers" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="resources/js/validatorForm.js"></script>
    <script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>