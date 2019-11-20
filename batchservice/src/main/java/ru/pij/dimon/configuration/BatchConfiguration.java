package ru.pij.dimon.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.pij.dimon.batch.DeviceItemProcessor;
import ru.pij.dimon.batch.SensorValueItemProcessor;
import ru.pij.dimon.pojo.Device;
import ru.pij.dimon.pojo.SensorValue;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    @Qualifier("ds2EntityManagerFactory")
    private LocalContainerEntityManagerFactoryBean emf2;

    @Bean
    public DeviceItemProcessor processor1() {
        return new DeviceItemProcessor();
    }

    @Bean
    public SensorValueItemProcessor processor2(){return new SensorValueItemProcessor();}

    @Bean
    public JpaItemWriter<Device> writer1(){
        JpaItemWriter<Device> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf2.getObject());
        return writer;
    }

    @Bean
    public JpaItemWriter<SensorValue>writer2(){
        JpaItemWriter<SensorValue> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf2.getObject());
        return writer;
    }

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactory(
            ResourcelessTransactionManager txManager) throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(
                txManager);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public JobRepository jobRepository(MapJobRepositoryFactoryBean factory)
            throws Exception {
        return (JobRepository) factory.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }


}
