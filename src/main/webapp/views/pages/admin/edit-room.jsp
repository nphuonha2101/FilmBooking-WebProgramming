<%--
  Created by IntelliJ IDEA.
  User: ConMuaXaDan
  Date: 10/24/2023
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<fmt:setBundle basename="properties.messageAdmin" var="adminMsg"/>

<section class="section align-top admin-two-cols__wrapper centered-vertical-content">
    <div class="container ">
        <jsp:include page="/views/components/admin-panel.jsp"/>
    </div>
    <div class="container centered-vertical-content">

        <h2><fmt:message bundle="${adminMsg}" key="editRoom"/></h2>


        <div class="centered-vertical-content wrapper">

            <%-- Status Code Messages--%>
            <jsp:include page="/views/components/statusCodeMessage.jsp"/>

            <!-- text form in left -->
            <div class="wrapper centered-vertical-content">
                <div>
                    <c:set var="editRoom" value="${editRoom}"/>
                    <form method="post" action="<c:url value="${pageContext.request.contextPath}/admin/edit/room"/>">
                        <label for="room-id"><fmt:message bundle="${adminMsg}" key="roomID"/>:
                            <span class="warning-color"> *</span>
                        </label>
                        <input class="readonly-input" type="text"
                               placeholder="<fmt:message bundle="${adminMsg}" key="roomID"/>" name="room-id"
                               id="room-id"
                               value="${editRoom.roomID}"
                               readonly/>
                        <label for="room-name"><fmt:message bundle="${adminMsg}" key="roomName"/>:
                            <span class="warning-color"> *</span>
                        </label>
                        <input type="text" placeholder="<fmt:message bundle="${adminMsg}" key="roomName"/>"
                               name="room-name" id="room-name"
                               value="${editRoom.roomName}"
                               required/>

                        <label for="seat-rows"><fmt:message bundle="${adminMsg}" key="roomRows"/>:
                            <span class="warning-color"> *</span>
                        </label>
                        <input type="number" min="0" placeholder="<fmt:message bundle="${adminMsg}" key="roomRows"/>"
                               name="seat-rows" id="seat-rows"
                               value="${editRoom.seatRows}"
                               required/>
                        <label for="seat-cols"><fmt:message bundle="${adminMsg}" key="roomCols"/>:
                            <span class="warning-color"> *</span>
                        </label>
                        <input type="number" min="0" placeholder="<fmt:message bundle="${adminMsg}" key="roomCols"/>"
                               name="seat-cols" id="seat-cols"
                               value="${editRoom.seatCols}"
                               required/>
                        <label for="theater-id"><fmt:message bundle="${adminMsg}" key="theaterAgency"/>:
                            <span class="warning-color"> *</span>
                        </label>
                        <select id="theater-id" name="theater-id">
                            <c:forEach var="theater" items="${theaters}">
                                <c:choose>
                                    <c:when test="${editRoom.theater.theaterID eq theater.theaterID}">
                                        <option selected value="${theater.theaterID}">${theater.theaterName}
                                            - ${theater.theaterAddress}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${theater.theaterID}">${theater.theaterName}
                                            - ${theater.theaterAddress}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <div class="centered-horizontal-content">
                                <input class="primary-filled-button button" type="submit"
                                       value="<fmt:message bundle="${adminMsg}" key="editRoom"/>">
                            </div>
                        </select>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>