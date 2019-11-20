<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="header.jsp"/>

<sec:authorize access="hasRole('ADMIN')">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trigger.js"></script>
</sec:authorize>

<div class="container-fluid">
    <div class="row">
        <nav class="col-12 col-md-3 col-lg-1 pl-2" style="background-color: #9BBB59E8" >
            <jsp:include page="sidebar.jsp"/>
        </nav>
        <div class="col-12 col-md-9 col-lg-11">
            <h1 align="center">Catalog of devices</h1>
            <table class="table">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Number</th>
                    <th scope="col">Type</th>
                    <th scope="col">IP</th>
                    <th scope="col">City</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="device" items="${devices}">
                    <tr>
                        <th scope="row">${device.id}</th>
                        <td> <a class="text-dark" href="${pageContext.request.contextPath}/sensorscatalog/sensors/${device.id}">${device.type}</a></td>
                        <td>${device.location.ip}</td>
                        <td>${device.location.city}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="dropdown-divider my-2"></div>
             <sec:authorize access="hasRole('ADMIN')">
                         <c:forEach var="device" items="${devices}">
                              <a class="btn" id="deviceButton${device.id}" role="button">Device ${device.id}</a>
                         </c:forEach>
              </sec:authorize>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>