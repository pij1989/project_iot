<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<form class="needs-validation mx-auto mt-5" action="${pageContext.request.contextPath}/signup/user"  method="post" novalidate>
    <h1 class="h3 mb-3 font-weight-normal text-center">Please sign up</h1>
    <div class="form-col">
        <div class="col-sm-4 mb-1 mx-auto">
            <label class="text-left" for="validationCustom01">First name</label>
            <input type="text" class="form-control" id="validationCustom01" placeholder="First name" name="firstName" required>
            <div class="invalid-feedback">
                Please choose a first name.
            </div>
        </div>
        <div class="col-sm-4 mb-1 mx-auto">
            <label for="validationCustom02">Last name</label>
            <input type="text" class="form-control" id="validationCustom02" placeholder="Last name" name="lastName" required>
            <div class="invalid-feedback">
                Please choose a last name.
            </div>
        </div>
        <div class="col-sm-4 mb-1 mx-auto">
            <label for="validationCustomUsername">Login</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-white" id="inputLoginGroupPrepend"><i class="fas fa-user"></i></span>
                </div>
                <input type="text" class="form-control " id="validationCustomUsername" placeholder="Login"  name="login" aria-describedby="inputLoginGroupPrepend" required>
                <div class="invalid-feedback" id="invalidLogin">
                    Please choose a login.
                </div>
            </div>
        </div>
        <div class="col-sm-4 mb-1 mx-auto">
            <label for="validationCustomUsername">Email</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-white" id="inputEmailGroupPrepend"><i class="fas fa-envelope"></i></span>
                </div>
                <input type="email" class="form-control" id="validationCustomEmail" placeholder="Email"  name="email" aria-describedby="inputEmailGroupPrepend" required>
                <div class="invalid-feedback" id="invalidEmail">
                    Please choose a email.
                </div>
            </div>
        </div>
        <div class="col-sm-4 mb-1 mx-auto">
            <label for="validationCustomPassword">Password</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-white" id="inputPasswordGroupPrepend"><i class="fas fa-lock"></i></span>
                </div>
                <input type="password" class="form-control" id="validationCustomPassword" placeholder="Password" name="password" aria-describedby="inputPasswordGroupPrepend"required>
                <div class="invalid-feedback" id="invalidPassword">
                    Please choose a password.
                </div>
            </div>
        </div>
        <div class="col-sm-4 mb-1 mx-auto">
            <label for="validationCustomPassword">Confirm password</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text bg-white" id="inputConfirmPasswordGroupPrepend" ><i class="fas fa-lock"></i></span>
                </div>
                <input type="password" class="form-control" id="validationCustomConfirmPassword" placeholder="Confirm password"  name="confirmPassword" aria-describedby="inputConfirmPasswordGroupPrepend" required>
                <div class="invalid-feedback" id="invalidConfirmPassword">
                    Please choose a confirm password.
                </div>
            </div>
        </div>
    </div>
    <div class="text-center my-3">
        <button class="btn btn-primary mx-auto" type="submit">Sign up</button>
    </div>
</form>

<c:if test="${errors != null}">

    <div class="alert alert-danger" role="alert">
        <c:forEach var="error" items="${errors}">
            <p align="center">${error.defaultMessage}</p>
        </c:forEach>
    </div>

</c:if>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/signup.js"></script>

<jsp:include page="footer.jsp"/>