package ru.pij.dimon.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import ru.pij.dimon.pojo.Device;
import ru.pij.dimon.pojo.SensorValue;
import ru.pij.dimon.service.NotificationService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public class BatchService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    @Qualifier("ds1EntityManagerFactory")
    private LocalContainerEntityManagerFactoryBean emf1;

    @Autowired
    @Qualifier("ds2TransactionManager")
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DeviceItemProcessor processor1;

    @Autowired
    private SensorValueItemProcessor processor2;

    @Autowired
    private JpaItemWriter<Device> writer1;

    @Autowired
    private JpaItemWriter<SensorValue> writer2;


    private Map<String,Object> parametrValues;

    @Scheduled(cron = "0/10 * * * * *")
    public void readWriteDataFromBaseToBase() throws Exception
    {
        if(notificationService.getNotificationDate() == null){
            parametrValues = Map.of("param",new Timestamp(0L));
        }else {
            parametrValues = Map.of("param",notificationService.getNotificationDate());
        }
        System.out.println(" Job Started at :"+ new Date());
        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();
        JobExecution execution = jobLauncher.run(importUserJob(), param);
        System.out.println("Job finished with status :" + execution.getStatus());
    }

    private JpaPagingItemReader<Device>reader1() {
        JpaPagingItemReader<Device> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf1.getObject());
        reader.setQueryString("select d from Device d");
        reader.setPageSize(100);
        return reader;
    }

    private JpaPagingItemReader<SensorValue> reader2() {
        JpaPagingItemReader<SensorValue> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf1.getObject());
        reader.setQueryString("select sv from SensorValue sv where sv.date > :param");
        reader.setParameterValues(parametrValues);
        return reader;
    }



    private Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .next(step2())
                .end()
                .build();
    }


    private Step step1() {
        return stepBuilderFactory.get("step1")
                .transactionManager(transactionManager)
                .<Device, Device> chunk(10)
                .reader(reader1())
                .processor(processor1)
                .writer(writer1)
                .build();
    }


    private Step step2() {
        return stepBuilderFactory.get("step2")
                .allowStartIfComplete(true)
                .transactionManager(transactionManager)
                .<SensorValue, SensorValue> chunk(10)
                .reader(reader2())
                .processor(processor2)
                .writer(writer2)
                .build();
    }
}
