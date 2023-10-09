<%--
  Created by IntelliJ IDEA.
  User: NhaNguyen
  Date: 03-Oct-23
  Time: 2:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<section class="section centered-vertical-content">
    <div class="container centered-vertical-content wrapper">
        <c:set var="film" value="${filmData}"/>

        <h1>${sectionTitle}</h1>

        <div id="film-details" class="wrapper">
            <div class="wrapper two-col__wrapper">

                <div class="film-img-in-card" style="background-image: url('<c:url value="${film.imgPath}"/>')"
                     id="film-img"></div>
                <div class="wrapper">
                    <h3> ${film.filmName}</h3>
                    <br>
                    <p>Giá vé: ${film.filmPrice} VNĐ/người</p>
                    <p>Đạo diễn: ${film.actors} </p>
                    <p>Độ dài phim: ${film.filmLength} phút</p>
                    <br>
                    <p>Mô tả: ${film.filmDescription}</p>

                </div>
                <div class="wrapper">
                    <select name="test">
                        <c:forEach var="test" begin="1" end="10" varStatus="loop">
                            <option value="test"> ${test} Option </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="wrapper">
                    <h3>Chon ghe</h3>
                    <table>
                        <tbody>
                        <c:forEach var="row" begin="0" end="10" varStatus="loop">
                            <tr>
                                <c:forEach var="col" begin="0" end="8" varStatus="loop">
                                    <td style="padding: 0">
                                        <c:choose>
                                            <c:when test="${col eq '5'}">
                                                <button class="outlined-button deactiv" onclick="alert(this.textContent + ' Not available')">${row} ${col}</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="outlined-button" onclick="alert(this.textContent); if (this.classList.contains('primary-filled-button')) {this.classList.remove('primary-filled-button')} else {this.classList.add('primary-filled-button')};">${row} ${col}</button>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </c:forEach>

                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>

        </div>
    </div>

</section>

