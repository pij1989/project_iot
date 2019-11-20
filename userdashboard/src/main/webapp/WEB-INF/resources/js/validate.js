const VALIDATE_PATTERN = /^(\d{2})(\.)(\d{2})(\.)(\d{4}) (0[0-9]|[1][0-9]|[2][0-3])[\:](0[0-9]|[1-5][0-9])[\:](0[0-9]|[1-5][0-9])$/;

function validateDate()
{
    let fromDate = document.getElementById("fromDate");
    let toDate = document.getElementById("toDate");
    let form = document.getElementById("filterByDate");

    form.addEventListener("submit",event=>{
        if(!isValidDate(fromDate) || !isValidDate(toDate)){
            event.preventDefault();
            event.stopPropagation();
        }
    })

}

function isValidDate(date) {
    console.log(date.value);
    if (date.value == null) return false;

    let dateValues = date.value.trim().match(VALIDATE_PATTERN);


    if (dateValues == null){
        swal("Date should be in the format of dd.mm.yyyy hh:mm:ss","","error");
        return false;
    }

    let dateYear = dateValues[5];
    let dateMonth = dateValues[3];
    let dateDay = dateValues[1];


    if ((dateMonth < 1) || (dateMonth > 12))
    {
        swal("Invalid date, amount of month must be from 1 to 12","","error");
        return false;
    }
    else if ((dateDay < 1) || (dateDay> 31))
    {
        swal("Invalid date, amount of day must be from 1 to 31","","error");
        return false;
    }
    else if ((dateMonth==4 || dateMonth==6 || dateMonth==9 || dateMonth==11) && dateDay ==31)
    {
        swal("Invalid date, april,june,september and november has 30 day","","error");
        return false;
    }
    else if (dateMonth ==2){
        let isleap = (dateYear % 4 == 0 && (dateYear % 100 != 0 || dateYear % 400 == 0));
        if (dateDay> 29 || (dateDay ==29 && !isleap))
        {
            swal("Invalid date, incorrect amount of day in february","","error");
            return false;
        }
    }
    return true;

}
