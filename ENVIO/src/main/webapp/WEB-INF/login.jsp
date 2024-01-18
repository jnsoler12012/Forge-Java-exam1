<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">

</head>
<body>
    <header class="bg-primary p-2 mb-5">
    	
		<nav class="navbar">
            <div class="container-fluid">
                <div class="d-flex flex-row" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <h1>Login</h1>
                        </li>
                        <li class="nav-item">
                            <h3><a class="nav-link text-light " href="/register">Register</a></h3>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <main class="row p-2">
        <div class="col">
            <h2>Login</h2>
            <c:if test="${ error != null }">
                <p class="text-danger"><c:out value="${ error }"/></p>
            </c:if>
            <form:form class="w-50" action="/login" method="POST"  modelAttribute="loginUser">
    
                <div class="m-4 form-group">
                    <form:input class="form-control col-6" type="email" path="email" placeholder="email"/>
                    <form:errors class="text-danger col-6" path="email"/>
                </div>
                <div class="m-4 form-group">
                    <form:input class="form-control col-6" type="password" path="password" placeholder="password"/>
                    <form:errors class="text-danger col-6" path="password"/>
                </div>
                <div class="m-4 form-group">
                    <input class="btn btn-success" type="submit" value="Login">
                </div>
            </form:form>
            <c:if test="${logoutMessage != null}">
                <p class="text-danger"><c:out value="${logoutMessage}"></c:out></p>
            </c:if>
            <c:if test="${errorMessage != null}">
                <p class="text-danger"><c:out value="${errorMessage}"></c:out></p>
            </c:if>
        </div>
    </main>
    
</body>
</html>