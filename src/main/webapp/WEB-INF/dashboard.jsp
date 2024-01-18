<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Dashboard</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
                        rel="stylesheet"
                        integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
                        crossorigin="anonymous">
                </head>

                <body>
                    TEststststt
                    <c:out value="${user.firstName}" />
                    <c:out value="${user.roles[0].getName()}" />

                    <body>
                        <header class="bg-primary p-2 mb-5">
                            <nav class="navbar navbar-expand-lg">
                                <div class="container-fluid">
                                    <div class="collapse navbar-collapse" id="navbarNav">
                                        <ul class="navbar-nav">
                                            <li class="nav-item">
                                                <a class="nav-link active" aria-current="page" href="/dashboard">
                                                    Welcome
                                                    <c:out value="${user.firstName}" />
                                                    <c:if test="${user.roles[0].name == 'ROLE_USER'}">
                                                        Usuario
                                                    </c:if>
                                                    <c:if test="${user.roles[0].name == 'ROLE_ADMIN'}">
                                                        Admin
                                                    </c:if>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <form id="logoutForm" method="POST" action="/logout">
                                                    <input type="hidden" name="${_csrf.parameterName }"
                                                        value="${_csrf.token }" />
                                                    <input type="submit" class="btn btn-success" value="Logout">
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </nav>
                        </header>

                        <main>
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3>
                                            Customers
                                        </h3>
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>
                                                        Name
                                                    </th>
                                                    <th>
                                                        Due instance
                                                    </th>
                                                    <th>
                                                        Amount Due
                                                    </th>
                                                    <th>
                                                        Package type
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:if test="${packages == null || packages == []}">
                                                    <tr>
                                                        <td>No hay valores, agregue una tabla</td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${packages != null}">

                                                    <c:forEach items="${packages}" var="packageVar">

                                                        <tr>
                                                            <td>

                                                                <c:out value="${packageVar.user.firstName}" />
                                                                <c:out value="${packageVar.user.lastName}" />

                                                            </td>
                                                            <td>
                                                                <c:out value="${packageVar.createdAt}" />
                                                            </td>
                                                            <td>
                                                                <fmt:setLocale value="en_US" />
                                                                <fmt:formatNumber value="${packageVar.cost}"
                                                                    type="currency" />
                                                            </td>
                                                            <td>
                                                                <c:out value="${packageVar.name}" />
                                                            </td>
                                                        </tr>


                                                    </c:forEach>
                                                </c:if>
                                            </tbody>
                                        </table>
                                        <hr>
                                        <h3>
                                            Packages
                                        </h3>
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>
                                                        Package Name
                                                    </th>
                                                    <th>
                                                        Product Cost
                                                    </th>
                                                    <th>
                                                        Available
                                                    </th>
                                                    <th>
                                                        Users
                                                    </th>
                                                    <th>
                                                        Actions
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:if test="${packagesCounter == null || packagesCounter == []}">
                                                    <tr>
                                                        <td>No hay valores, agregue una tabla</td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${packagesCounter != null}">
                                                    <c:forEach items="${packagesCounter}" var="packageVar">
                                                        <tr>
                                                            <td>

                                                                <c:out value="${packageVar.key.name}" />

                                                            </td>
                                                            <td>
                                                                <fmt:setLocale value="en_US" />
                                                                <fmt:formatNumber value="${packageVar.key.cost}"
                                                                    type="currency" />
                                                            </td>
                                                            <td>
                                                                <c:if test="${packageVar.key.available == true}">
                                                                    Active
                                                                </c:if>
                                                                <c:if test="${packageVar.key.available == false}">
                                                                    Inactive
                                                                </c:if>
                                                            </td>
                                                            <td>
                                                                <c:out value="${packageVar.value}" />
                                                            </td>
                                                            <td>
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <c:if test="${packageVar.value > 0}">
                                                                            deactivate
                                                                        </c:if>
                                                                        <c:if
                                                                            test="${packageVar.value < 0 && packageVar.key.available == true}">
                                                                            deactivateaaa
                                                                        </c:if>
                                                                        <c:if
                                                                            test="${packageVar.value < 0 && packageVar.key.available == false}">
                                                                            activatee
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="col-md-2 border-left">
                                                                        <c:if
                                                                            test="${user.roles[0].name == 'ROLE_ADMIN'}">
                                                                            <a
                                                                                href="/packages/${packageVar.key.id}/edit">editar</a>
                                                                        </c:if>
                                                                        <c:if
                                                                            test="${user.roles[0].name != 'ROLE_ADMIN'}">
                                                                            can not edit
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>

                                            </tbody>
                                        </table>
                                        <hr>
                                        <form:form action="/package/new" method="POST" modelAttribute="package1">
                                            <div class="form-group">
                                                <c:if test="${ errorName != null }">
                                                    <div class="alert alert-danger" role="alert">
                                                        <c:out value="${errorName}" />
                                                    </div>
                                                </c:if>
                                                <form:label path="name" for="name">Package Name</form:label>
                                                <form:errors path="name" class="badge text-bg-primary" />
                                                <form:input type="text" class="form-control" id="name" path="name" />
                                            </div>
                                            <div class="form-group">
                                                <form:label path="cost" for="cost">Cost</form:label>
                                                <form:errors path="cost" class="badge text-bg-primary" />
                                                <form:input type="text" class="form-control" id="cost" path="cost" />
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-primary">
                                                    Create new package
                                                </button>
                                            </div>

                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </main>

                    </body>
                </body>

                </html>