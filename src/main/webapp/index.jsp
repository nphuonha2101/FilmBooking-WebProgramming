<%@ page import="com.filmbooking.DAOservices.FilmDAOServicesImpl" %><%--
  Created by IntelliJ IDEA.
  User: NhaNguyen
  Date: 16-09-2023
  Time: 7:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Film Booking</title>
    <%@include file="head-links.jsp" %>
    <%@include file="already-login-nav.jsp" %>
</head>
<style>
    <%@include file="resources/css/style.css" %>
</style>
<body>

<section class="slides-show section">
    <div class="slides-show wrapper">
        <h1>Our new films: </h1>
        <div class="slides wrapper">

            <!-- create film cards -->
            <c:set var="filmList" value="<%=new FilmDAOServicesImpl().getAll()%>"/>
            <c:forEach var="film" items="${filmList}" varStatus="loop">
                <div class="slide-cards container" id="card-${loop.index}" onclick="">

                    <h2>${film.filmName}</h2>
                    <p>Room: ${film.roomID}</p>
                    <p>Price: ${film.price} VND/person</p>
                    <form action="handles-data-modal" class="hidden-form" id="hidden-form" method="get">
                        <input type="hidden" name="filmID" value="${film.filmID}">
                    </form>
                        <button class="light-filled-button button show-modal-button">Booking now</button>
                </div>
            </c:forEach>

        </div>
    </div>

    <%@include file="register-modal.jsp"%>
</section>

<%@include file="footer.jsp" %>
</body>
</html>
