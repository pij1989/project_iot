const WINDOW_WIDTH = 768;
<!-- Resize sidebar, when width less then 768 px -->
$( window ).on("load",handlerResize);
$( window ).on("resize",handlerResize);

function handlerResize() {
    let width = $( this ).width();
    if(width<WINDOW_WIDTH){
        $("#sizebar").css("height","auto");
    } else {
        $("#sizebar").css("height","100vh");
    }
    console.log(width);
}

