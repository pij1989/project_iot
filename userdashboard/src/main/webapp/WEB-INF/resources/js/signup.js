const MIN_PASSWORD_LENGHT = 8;
const REG_EX_EMAIL = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/;
const REG_EX_PASSWORD =/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}/;

window.addEventListener("load",event=>{
    let form = document.querySelector('.needs-validation');
    let password = document.getElementById("validationCustomPassword");
    let confirmPassword = document.getElementById("validationCustomConfirmPassword");

    Array.prototype.forEach.call(form.elements,element=>{
        if (element.type === "text"){
            let invalidLogin = document.getElementById("invalidLogin");
            element.addEventListener("input", event => {
                if(element.value.trim() === ""){
                    addIsInvalid(element);
                    invalidLogin.innerText = "Please choose a login."

                } else {
                    addIsValid(element);
                }
                if(element === document.getElementById("validationCustomUsername")){
                    let login = element.value;
                    console.log(login);
                    $.ajax({
                        url: "signup/login",
                        type: "POST",
                        data:{login:login},
                        success: function(answer) {
                            if(answer === "false"){
                                addIsInvalid(element);
                                invalidLogin.innerText = "Login "+login+" has alredy existed"
                            }
                        },
                    });
                }
            })
        }
        if(element.type === "email"){
            let invalidEmail = document.getElementById("invalidEmail");
            element.addEventListener("input",event=>{
                let validEmail = REG_EX_EMAIL.test(element.value);
                if (!validEmail) {
                    addIsInvalid(element);
                    invalidEmail.innerText = "Please enter correct a email.";
                } else {
                    addIsValid(element);
                }
                if(element.value === ""){
                    addIsInvalid(element);
                    invalidEmail.innerText = "Please choose a email.";
                }
            })
        }
    });

    password.addEventListener("input", event=>{
        let invalidPassword = document.getElementById("invalidPassword");
        let passwordValue = password.value.toString();
        if(passwordValue.length < MIN_PASSWORD_LENGHT){
            addIsInvalid(password);
            invalidPassword.innerText = "Please enter a password no less 8 sign."
        } else {
            if(!(REG_EX_PASSWORD.test(passwordValue))){
                addIsInvalid(password);
                invalidPassword.innerText = "Password must contain a number and [a-z],[A-Z] character"
            }else {
                addIsValid(password);
            }
        }

        if(passwordValue === ""){
            addIsInvalid(password);
            invalidPassword.innerText = "Please choose a password."
        }
    });

    confirmPassword.addEventListener("input",event=>{
        console.log(confirmPassword.value);
        let invalidConfirmPassword = document.getElementById("invalidConfirmPassword");
        if(confirmPassword.value !== password.value || !(REG_EX_PASSWORD.test(confirmPassword.value))){
            addIsInvalid(confirmPassword);
            invalidConfirmPassword.innerText = "Confirm password must be equals entered password. "
        } else {
            addIsValid(confirmPassword);
        }
        if(confirmPassword.value === ""){
            addIsInvalid(confirmPassword);
            invalidConfirmPassword.innerText = "Please choose a confirm password."
        }
    });
    <!-- Validate submit -->
    form.addEventListener("submit",event=>{
        let elements = form.elements;
        if(isValid(elements)){
            event.preventDefault();
            event.stopPropagation();
            form.submit();
            form.reset();
            deleteClassIsValid(elements);
        } else {
            event.preventDefault();
            event.stopPropagation();
        }
    })
});

function  isValid (elements) {
    let isValid = true;
    Array.prototype.forEach.call(elements,element=>{
        if(element.type !== "submit"){
            if(element.value === "" && !element.classList.contains('is-invalid')){
                element.classList.add('is-invalid')
            }
            if(element.classList.contains('is-invalid')){
                isValid = false;
            }
        }
    });
    return isValid;
}

function deleteClassIsValid(elements) {
    Array.prototype.forEach.call(elements,element=>{
        element.classList.remove('is-valid');
    });

}

function addIsInvalid(element) {
    if(element.classList.contains('is-valid')) {element.classList.remove('is-valid')}
    element.classList.add('is-invalid');
}

function addIsValid(element) {
    if(element.classList.contains('is-invalid')) {element.classList.remove('is-invalid')}
    element.classList.add('is-valid');
}

$(window).on("load",function(){
    $("#inputPasswordGroupPrepend,#inputConfirmPasswordGroupPrepend").on("click",function () {
        if($(this).find("i").hasClass("fas fa-lock")){
            $(this).find("i").removeClass("fas fa-lock");
            $(this).find("i").addClass("fas fa-lock-open");
            if($(this).attr("id") === "inputPasswordGroupPrepend"){
                $("#validationCustomPassword").attr("type","text");
            } else {
                $("#validationCustomConfirmPassword").attr("type","text");
            }
        } else {
            $(this).find("i").removeClass("fas fa-lock-open");
            $(this).find("i").addClass("fas fa-lock");
            if($(this).attr("id") === "inputPasswordGroupPrepend"){
                $("#validationCustomPassword").attr("type","password");
            } else {
                $("#validationCustomConfirmPassword").attr("type","password");
            }
        }
    });
});



