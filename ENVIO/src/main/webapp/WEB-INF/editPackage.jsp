<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Edit package</title>
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
                        <form:form class="form w-75 m-auto" action="/packages/${ packageToEdit.id }/edit" method="POST"
                            modelAttribute="packageCustomized">

                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h3>Package Name</h3>
                                    </div>
                                    <div class="col-md-6">
                                        <h3>
                                            <c:out value="${packageToEdit.name}" />
                                        </h3>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <form:label path="cost" for="cost">Costo</form:label>
                                <form:errors path="cost" class="badge text-bg-primary" />
                                <c:if test="${ packageToEdit.cost != null}">
                                    <form:input type="text" class="form-control" id="cost" path="cost"
                                        value="${packageToEdit.cost}" />
                                </c:if>
                                <c:if test="${ packageToEdit.cost == null}">
                                    <form:input type="text" class="form-control" id="cost" path="cost" />
                                </c:if>
                            </div>

                            <input type="submit" class="btn btn-success" value="Change">

                        </form:form>
                        <c:if test="${user.roles[0].name == 'ROLE_ADMIN' && packageToEdit.user == null}">
                            <div class="form-group">
                                <button class="btn btn-danger"
                                    onclick="location.href='/packages/${packageToEdit.id}/delete'"
                                    type="button">Delete</button>
                            </div>
                        </c:if>
                    </main>
                </body>

                </html>