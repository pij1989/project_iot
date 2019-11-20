<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<div class="alert alert-danger" role="alert">
    <c:forEach var="error" items="${errors}">
        <h1 align="center">${error.defaultMessage}</h1>
    </c:forEach>
</div>

<jsp:include page="footer.jsp"/>