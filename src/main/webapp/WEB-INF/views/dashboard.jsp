<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
            <h1 id="homeTitle">
               <c:out value="${nbComputers}"/> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" value="${search}" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="ListComputers" method="POST">
            <input type="hidden" id="selection" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                       
                               <p>
                                Computer name
                               
			            		<a href="ListComputers?taillePage=${taillePage}&orderby=computer.name.ASC" >
			            			<span class="glyphicon glyphicon-sort-by-alphabet"></span>
			            		</a>
			            		
			            		<a href="ListComputers?taillePage=${taillePage}&orderby=computer.name.DESC">
			            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
			            		</a>
								</p>
                        </th>
                        <th>
                              <p>
                                Introduced Date
                               
			            		<a href="ListComputers?orderby=computer.introduced.ASC">
			            			<span class="glyphicon glyphicon-sort-by-order"></span>
			            		</a>
			            		
			            		<a  href="ListComputers?orderby=computer.introduced.DESC">
			            			<span class="glyphicon glyphicon-sort-by-order-alt"> </span>
			            		</a>
								</p>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                              <p>
                                Discontinued Date
                               
			            		<a href="ListComputers?orderby=computer.discontinued.ASC">
			            			<span class="glyphicon glyphicon-sort-by-order"></span>
			            		</a>
			            		
			            		<a  href="ListComputers?orderby=computer.discontinued.DESC">
			            			<span class="glyphicon glyphicon-sort-by-order-alt"> </span>
			            		</a>
								</p>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <p>
                                Company
                               
			            		<a href="ListComputers?orderby=company_name.ASC">
			            			<span class="glyphicon glyphicon-sort-by-alphabet"></span>
			            		</a>
			            		
			            		<a  href="ListComputers?orderby=company_name.DESC">
			            			<span class="glyphicon glyphicon-sort-by-alphabet-alt"> </span>
			            		</a>
								</p>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">

				 <c:forEach items="${computersList}" var="computer">
				                    <tr>
				                        <td class="editMode">
				                            <input type="checkbox" name="cb" class="cb" value="${computer.idComputer}">
				                        </td>
				                        <td>
				                            <a href="editComputer?idComputer=${computer.idComputer}" onclick=""><c:out value="${computer.name}"></c:out></a>
				                        </td>
				                        <td><c:out value="${computer.introduced}"></c:out> </td>
				                        <td><c:out value="${computer.discontinued}"></c:out> </td>
				                        <td><c:out value="${computer.company.nameCompany}"></c:out></td>
				                    </tr>
				</c:forEach>                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
          <li>
              	<c:if test="${pageIterator>1}">
              		<a href="ListComputers?pageIterator=${pageIterator-1}&search=${search}&orderby=${orderby}&taillePage=${taillePage}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
				</c:if>      
              </li> 
              <c:forEach  var = "i" begin = "0" end = "5">
              <c:if test="${pageIterator+i<=maxPages}">
              
              <li><a href="ListComputers?pageIterator=${pageIterator+i}&search=${search}&orderby=${orderby}&taillePage=${taillePage}"><c:out value="${pageIterator+i}"></c:out></a></li>			  
			  </c:if>
			  </c:forEach>
                
          	  <li>
              
              <c:if test="${pageIterator<maxPages}">
                <a href="ListComputers?pageIterator=${pageIterator+1}&search=${search}&orderby=${orderby}&taillePage=${taillePage}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
                </c:if>
		</li>
        </ul>
		
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" onclick="window.location.href='ListComputers?taillePage=10&search=${search}&orderby=${orderby}'">10</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='ListComputers?taillePage=50&search=${search}&orderby=${orderby}'">50</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='ListComputers?taillePage=100&search=${search}&orderby=${orderby}'">100</button>
        </div>

    </footer>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>

</body>
</html>