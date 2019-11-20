<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <nav class="col-12 col-md-3 col-lg-1 pl-2" style="background-color: #9BBB59E8" >
            <jsp:include page="sidebar.jsp"/>
        </nav>
        <div class="col-12 col-md-9 col-lg-11">
            <h1 align="center">Catalog of sensors</h1>
            <table class="table">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Number</th>
                    <th scope="col">Type</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sensor" items="${sensors}">
                    <tr>
                        <th scope="row">${sensor.id}</th>
                        <td> <a class="text-dark" href="${pageContext.request.contextPath}/sensorscatalog/sensors/sensor/${sensor.id}">${sensor.type}</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>