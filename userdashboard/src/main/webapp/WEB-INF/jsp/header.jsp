<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Leaflet map -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css" integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==" crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js" integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og==" crossorigin=""></script>
    <!-- SweetAlert -->
    <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.min.css'>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>





</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" style="font-size: 35px" href="${pageContext.request.contextPath}"><img src="https://img.icons8.com/ios-filled/50/000000/iota.png">  IoT</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <c:choose>
            <c:when test="${sensors != null}"><form class="form-inline ml-lg-5 pl-lg-5 my-2 my-lg-0" action="${pageContext.request.contextPath}/searchsensor"></c:when>
            <c:otherwise><form class="form-inline ml-lg-5 pl-lg-5 my-2 my-lg-0" action="${pageContext.request.contextPath}/searchdevice"></c:otherwise>
        </c:choose>
        <form class="form-inline ml-lg-5 pl-lg-5 my-2 my-lg-0" action="${pageContext.request.contextPath}/searchsensor">
            <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><i class="fas fa-search"></i>  Search</button>
        </form>
        <ul class="navbar-nav ml-auto">
        <sec:authorize access="isAuthenticated()">
            <li class="nav-item dropdown"id="userLabel">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <i class="fas fa-user"></i>  <span id = "userLogin"> </span>
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                  <a class="dropdown-item" id="logOut" href="${pageContext.request.contextPath}/logout" role="button">Log Out</a>
                </div>
             </li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
             <li class="nav-item active">
                  <a class="btn btn-outline-primary ml-lg-2 mt-2 my-lg-0" href="${pageContext.request.contextPath}/login" role="button">Log In</a>
             </li>
             <li class="nav-item active">
                  <a class="btn btn-primary ml-lg-2 mt-2 my-lg-0" href="${pageContext.request.contextPath}/signup" role="button">Sign Up</a>
             </li>
        </sec:authorize>
        </ul>
    </div>
</nav>

<div class="dropdown-divider my-0"></div>
