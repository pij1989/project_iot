package ru.pij.dimon.device;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.service.DeviceType;
import ru.pij.dimon.service.SensorType;

import java.util.*;
import java.util.concurrent.*;

public class DeviceHumidityIlluminationTemperature extends Device {
    private static final String DEVICE_SERIAL_NUMBER="12AC5600214";
    private static final int SIZE_BUFFER_TEMPERATURE_SENSOR = 5;
    private static final int SIZE_BUFFER_ILLUMINATION_SENSOR = 5;
    private static final int SIZE_BUFFER_HUMIDITY_SENSOR = 5;
    private static final int AMOUNT_THREAD = 3;
    private static final long TIME_OUT = 300;

    private int minTemperature;
    private int maxTemperature;
    private int minIllumination;
    private int maxIllumination;
    private int minHumidity;
    private int maxHumidity;

    private BlockingQueue<Integer> queueTemperature;
    private BlockingQueue<Integer> queueIllumination;
    private BlockingQueue<Integer> queueHumidity;
    private ExecutorService pool;
    private final Random random = new Random();
    private static Logger log = LogManager.getLogger(DeviceHumidityIlluminationTemperature.class);

    private DeviceHumidityIlluminationTemperature(DeviceBuilder builder){
        super(DeviceType.TEMPERATURE_ILLUMINATION_HUMIDITY_DEVICE.getType(),DEVICE_SERIAL_NUMBER);
        minTemperature = builder.minTemperature;
        maxTemperature = builder.maxTemperature;
        minIllumination = builder.minIllumination;
        maxIllumination = builder.maxIllumination;
        minHumidity = builder.minHumidity;
        maxHumidity = builder.maxHumidity;
    }

    public static class DeviceBuilder{
        private int minTemperature = 15;
        private int maxTemperature = 30;
        private int minIllumination = 150;
        private int maxIllumination = 200;
        private int minHumidity = 30;
        private int maxHumidity = 45;

        public DeviceBuilder minTemperature(int minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public DeviceBuilder maxTemperature(int maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public DeviceBuilder minIllumination(int minIllumination) {
            this.minIllumination = minIllumination;
            return this;
        }

        public DeviceBuilder maxIllumination(int maxIllumination) {
            this.maxIllumination = maxIllumination;
            return this;
        }

        public DeviceBuilder minHumidity(int minHumidity) {
            this.minHumidity = minHumidity;
            return this;
        }

        public DeviceBuilder maxHumidity(int maxHumidity) {
            this.maxHumidity = maxHumidity;
            return this;
        }

        public DeviceHumidityIlluminationTemperature build(){
            return new DeviceHumidityIlluminationTemperature(this);
        }

    }

    @Override
    public void startDevice(){
        try {
            if(queueTemperature == null) this.queueTemperature = new ArrayBlockingQueue<>(SIZE_BUFFER_TEMPERATURE_SENSOR);
            if(queueIllumination == null) this.queueIllumination = new ArrayBlockingQueue<>(SIZE_BUFFER_ILLUMINATION_SENSOR);
            if(queueHumidity == null) this.queueHumidity = new ArrayBlockingQueue<>(SIZE_BUFFER_HUMIDITY_SENSOR);

            pool = Executors.newFixedThreadPool(AMOUNT_THREAD);
            pool.execute(getTask(minTemperature,maxTemperature,queueTemperature));
            pool.execute(getTask(minIllumination,maxIllumination,queueIllumination));
            pool.execute(getTask(minHumidity,maxHumidity,queueHumidity));

            isStart = true;
            log.info("Device is started");
        } catch (Exception e) {
            e.printStackTrace();
            pool.shutdown();
            isStart = false;
            log.error(e);
        }
    }

    @Override
    public void stopDevice(){
        if(isStart){
            pool.shutdown();
            isStart = false;
            log.info("Device is stoped");
        }
    }

    @Override
    public Integer getSensorValue(SensorType sensorType) throws InterruptedException {
        if(sensorType == null){
            throw new IllegalArgumentException("Sensor type can not be null");
        }
        switch (sensorType){
            case TEMPERATURE_SENSOR:
                return queueTemperature.take();
            case ILLUMINATION_SENSOR:
                return queueIllumination.take();
            case HUMIDITY_SENSOR:
                return queueHumidity.take();
        }
        throw new NoSuchElementException(sensorType.getType()+"not found");
    }

    private Runnable getTask(final int minValue, final int maxValue, final BlockingQueue<Integer> blockingQueue){
        return ()->{
            try {
                while (true) {
                    if(pool.isShutdown()) break;
                    int value = random
                            .ints(minValue,maxValue)
                            .iterator()
                            .nextInt();
                    blockingQueue.put(value);
                    Thread.sleep(TIME_OUT);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
