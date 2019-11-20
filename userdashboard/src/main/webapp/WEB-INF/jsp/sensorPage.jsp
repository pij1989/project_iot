<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/geolocation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validate.js"></script>
<script>
    window.addEventListener("load",()=>{
        viewLocation('${pageContext.request.contextPath}/geolocation/${sensor.id}');
        viewChart('${jsonSensor}')
        validateDate();
    });
</script>
<ul class="nav justify-content-end">
    <li class="nav-item">
        <div class="btn-group bg-light">
            <button type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Filter by date
            </button>
            <div class="dropdown-menu dropdown-menu-right">
                <form id="filterByDate" class="px-4 py-3" action="${pageContext.request.contextPath}/filter/sensor/${sensor.id}">
                    <div class="form-group">
                        <label for="fromDate">From date</label>
                        <input type="text" class="form-control" name="fromDate" id="fromDate" style="width: 200px" placeholder="dd.mm.yyyy hh:mm:ss">
                    </div>
                    <div class="form-group">
                        <label for="toDate">To date</label>
                        <input type="text" class="form-control" name="toDate" id="toDate" style="width: 200px" placeholder="dd.mm.yyyy hh:mm:ss">
                    </div>
                    <button type="submit" class="btn btn-primary">Filter</button>
                </form>
            </div>
        </div>
    </li>
</ul>
<div class="container-fluid">
    <div class="row mb-5">
        <div class="col-12 col-md-6 col-lg-6">
            <h1 align="center">Chart of ${sensor.type}</h1>
            <canvas id="myChart" height="400" width="800"></canvas>
        </div>
        <div class="col-12 col-md-6 col-lg-6">
            <h1 align="center">Location of ${sensor.type}</h1>
            <div id="mapid"  style="width: 100%; height: 90%;"></div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h1 align="center">Information of ${sensor.type}</h1>
            <table class="table">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Number</th>
                    <th scope="col">Value, ${sensor.units}</th>
                    <th scope="col">Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="value" items="${sensor.sensorValueList}">
                    <tr>
                        <th scope="row">${value.id}</th>
                        <td>${value.value}</td>
                        <td>${value.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<div align="center" class="my-5">
    <a class="btn btn-success" href="${pageContext.request.contextPath}/pdf/sensor/${sensor.id}" role="button">Download PDF</a>
</div>

<jsp:include page="footer.jsp"/>