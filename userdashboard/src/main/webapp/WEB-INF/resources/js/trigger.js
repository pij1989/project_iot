$(window).on("load",()=>{
    let device1 = document.getElementById("deviceButton1");
    let device2 = document.getElementById("deviceButton2");
    if(device1 != null){
        ajaxQuery("http://localhost:8500/",device1);
    }
    if (device2 != null){
        ajaxQuery("http://localhost:8550/",device2);
    }
});

function ajaxQuery(url,device) {
    let name = device.innerText;
    let state = "on";
    let msg = "not connect";
    $.ajax({
        url:url,
        type: "GET",
        contentType:
            "application/x-www-form-urlencoded; charset=UTF-8",
        success: function(data) {
            state = data;
            triggerButton(device,state,name);
        },
        error: function(jqXHR, exception) {
            if (jqXHR.status === 0) {
                triggerButton(device,msg,name);
            }
            console.log(jqXHR.status);
            console.log(exception);
        }
    });

    device.addEventListener("click",()=> {
        $.ajax({
            url:url,
            type: "POST",
            contentType:
                "application/x-www-form-urlencoded; charset=UTF-8",
            data: $.param( {state : state} ),
            success: function(data) {
                state = data;
                triggerButton(device,state,name);
            },
            error: function(jqXHR, exception) {
                triggerButton(device,"error",name);
                console.log(jqXHR.status);
                console.log(exception);
            }
        });
    });
}

function triggerButton(element,state,name) {
    if(state === "off"){
        if(element.classList.contains('btn-danger')) {element.classList.remove('btn-danger')}
        element.classList.add('btn-success');
        element.innerText = name+" "+state;
        return;
    }
    if(state === "on"){
        if(element.classList.contains('btn-success')) {element.classList.remove('btn-success')}
        element.classList.add('btn-danger');
        element.innerText = name+" "+state;
        return;
    }

    if(element.classList.contains('btn-success')) {element.classList.remove('btn-success')}
    element.classList.add('btn-danger');
    element.innerText = name+" "+state;
}