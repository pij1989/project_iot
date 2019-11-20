package ru.pij.dimon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.pij.dimon.helper.DeviceHelper;
import ru.pij.dimon.helper.SensorHelper;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ServiceApplication {

    @Bean
    public DeviceHelper deviceHelper(){
        return new DeviceHelper();
    }

    @Bean
    public SensorHelper sensorHelper(){
        return new SensorHelper();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class,args);

    }
}
