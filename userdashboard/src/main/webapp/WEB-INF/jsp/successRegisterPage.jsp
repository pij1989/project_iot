<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <nav class="col-12 col-md-3 col-lg-1 pl-2" style="background-color: #9BBB59E8" >
            <jsp:include page="sidebar.jsp"/>
        </nav>
        <div class="col-12 col-md-9 col-lg-10">
            <h1 align="center" style="font-size: 35px">You are successefull registered. To continue please <a class="nav-link" href="${pageContext.request.contextPath}/login"> Log in</a></h1>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>

