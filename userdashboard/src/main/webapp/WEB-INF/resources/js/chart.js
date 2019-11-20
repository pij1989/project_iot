const ERROR = "error";
function viewChart(jsonSensor) {
    let values = [];
    let dates = [];
    if(jsonSensor !== ERROR){
        let sensor = JSON.parse(jsonSensor);
    sensor['sensorValueList'].forEach(sensorValue=>{
        values.push(sensorValue.value);
        dates.push(sensorValue.date);
    });
    getChart(values,dates,sensor.type.substring(0,sensor.type.lastIndexOf(' '))+" ("+sensor.units+")");
    console.log(values);
    console.log(dates);
    }
}

function getChart(data,labels,measure){
    var ctx = document.getElementById("myChart");
    var speedData = {
        labels,
        datasets: [{
            label: measure,
            data,
            backgroundColor: "rgba(75,192,192,0.4)",
            borderColor: "rgba(75,192,192,1)",
        }]
    };

    var chartOptions = {
        legend: {
            display: true,
            position: 'top',
            labels: {
                boxWidth: 80,
                fontColor: 'black'
            }
        }
    };

    var lineChart = new Chart(ctx, {
        type: 'line',
        data: speedData,
        options: chartOptions
    });
}


