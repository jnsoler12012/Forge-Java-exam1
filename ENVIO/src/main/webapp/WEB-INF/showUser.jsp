<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
                        rel="stylesheet"
                        integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
                        crossorigin="anonymous">
                </head>

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
                        <h1>Welcome,
                            <c:out value="${user.firstName}" />
                            <c:out value="${user.lastName}" />
                        </h1>
                        <th></th>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12">

                                    <c:if test="${packages == null || packages == []}">
                                        <tr>
                                            <td>No hay valores, agregue una tabla</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${packages != null}">

                                        <c:forEach items="${packages}" var="packageVar">
                                            <c:if test="${packageVar.user.id == user.id}">
                                                <div class="row">
                                                    <div class="col-md-6 mb-4">
                                                        <h3>Your current package:</h3>
                                                    </div>
                                                    <div class="col-md-6 mb-4">
                                                        <h3>
                                                            <c:out value="${packageVar.name}" />
                                                        </h3>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6 mb-4">
                                                        <h3>Package Cost:</h3>
                                                    </div>
                                                    <div class="col-md-6 mb-4">
                                                        <h3>
                                                            <fmt:setLocale value="en_US" />
                                                            <fmt:formatNumber value="${packageVar.cost}"
                                                                type="currency" />
                                                        </h3>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <h2>Switch type of package</h2>
                                                            <c:if test="${ errorName != null }">
                                                                <div class="alert alert-danger" role="alert">
                                                                    <c:out value="${errorName}" />
                                                                </div>
                                                            </c:if>
                                                            <form:form class="form w-75 m-auto"
                                                                action="/switchPackage/${ packageVar.id }" method="POST"
                                                                modelAttribute="packageCustomized">
                                                                <div class="form-group row">
                                                                    <form:select class="form-control " path="name">
                                                                        <c:forEach items="${ possiblePackages }"
                                                                            var="possPackage">
                                                                            <c:if
                                                                                test="${packageVar.name != possPackage}">
                                                                                <option value=${ possPackage }>${
                                                                                    possPackage }
                                                                                </option>
                                                                            </c:if>

                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>
                                                                <input type="submit" class="btn btn-success"
                                                                    value="Switch">
                                                            </form:form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <hr>
                                        </c:forEach>
                                    </c:if>


                                </div>
                            </div>
                        </div>
                    </main>

                </body>

                </html>