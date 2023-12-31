<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:choose>
    <c:when test="${empty sessionScope.lang || sessionScope.lang eq 'default'}">
        <fmt:setLocale value="default"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${sessionScope.lang}"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="properties.message" var="msg"/>
<fmt:setBundle basename="properties.pageTitle" var="pageTitle"/>
<fmt:setBundle basename="properties.statusCode" var="statusCodeMsg"/>

<section class="content section centered-vertical-content">
    <div class="centered-vertical-content container form__container">
        <h2 class="title"><fmt:message key="loginSectionTitle" bundle="${pageTitle}"/></h2>

        <%--        Status Code Messages--%>
        <jsp:include page="/views/components/statusCodeMessage.jsp"/>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="username">
                <span class="material-symbols-rounded">person</span>
                <fmt:message key="usernameOrEmail" bundle="${msg}"/>:
                <span class="warning-color"> *</span>
            </label>
            <input type="text" name="username" id="username"
                   placeholder=" <fmt:message key="usernameOrEmail" bundle="${msg}"/>"
                   autocomplete="true" required>

            <label for="password">
                <span class="material-symbols-rounded">password</span>
                <fmt:message key="password" bundle="${msg}"/>:
                <span class="warning-color"> *</span>
            </label>
            <input type="password" name="password" id="password"
                   placeholder=" <fmt:message key="password" bundle="${msg}"/>"
                   autocomplete="true" required>
            <input type="submit" class="primary-filled-button button"
                   value=" <fmt:message key="login" bundle="${msg}"/>">
        </form>
        <p><fmt:message key="dontHaveAccount" bundle="${msg}"/> <span><a class="links" href="${pageContext.request.contextPath}/signup"><fmt:message
                key="signupNow" bundle="${msg}"/> </a> </span>
        </p>
        <p><fmt:message key="Or" bundle="${msg}"/> <span><a class="links" href="${pageContext.request.contextPath}/forgot-password"><fmt:message
                key="youForgotPassword" bundle="${msg}"/></a> </span></p>
    </div>
</section>
