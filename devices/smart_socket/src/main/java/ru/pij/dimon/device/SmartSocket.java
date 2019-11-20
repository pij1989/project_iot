package ru.pij.dimon.device;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.service.DeviceType;
import ru.pij.dimon.service.SensorType;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartSocket extends Device {
    private static final String DEVICE_SERIAL_NUMBER="57QF9851375";
    private static final int SIZE_BUFFER_POWER_SENSOR = 5;
    private static final int SIZE_BUFFER_VOLTAGE_SENSOR = 5;
    private static final int SIZE_BUFFER_AMPERAGE_SENSOR = 5;
    private static final int AMOUNT_THREAD = 3;
    private static final long TIME_OUT = 300;

    private int minPower;
    private int maxPower;
    private int minVoltage;
    private int maxVoltage;
    private int minAmperage;
    private int maxAmperage;

    private BlockingQueue<Integer> queuePower;
    private BlockingQueue<Integer> queueVoltage;
    private BlockingQueue<Integer> queueAmperage;
    private ExecutorService pool;
    private final Random random = new Random();
    private static Logger log = LogManager.getLogger(SmartSocket.class);

    private SmartSocket(DeviceBuilder builder){
        super(DeviceType.SMART_SOCKET.getType(),DEVICE_SERIAL_NUMBER);
        minPower = builder.minPower;
        maxPower = builder.maxPower;
        minVoltage = builder.minVoltage;
        maxVoltage = builder.maxVoltage;
        minAmperage = builder.minAmperage;
        maxAmperage = builder.maxAmperage;
    }

    public static class DeviceBuilder{
        private int minPower = 1000;
        private int maxPower = 2000;
        private int minVoltage = 200;
        private int maxVoltage = 220;
        private int minAmperage = 5;
        private int maxAmperage = 10;

        public DeviceBuilder minPower(int minPower) {
            this.minPower = minPower;
            return this;
        }

        public DeviceBuilder maxPower(int maxPower) {
            this.maxPower = maxPower;
            return this;
        }

        public DeviceBuilder minVoltage(int minVoltage) {
            this.minVoltage = minVoltage;
            return this;
        }

        public DeviceBuilder maxVoltage(int maxVoltage) {
            this.maxVoltage = maxVoltage;
            return this;
        }

        public DeviceBuilder minAmperage(int minAmperage) {
            this.minAmperage = minAmperage;
            return this;
        }

        public DeviceBuilder maxAmperage(int maxAmperage) {
            this.maxAmperage = maxAmperage;
            return this;
        }

        public SmartSocket build(){
            return new SmartSocket(this);
        }

    }

    @Override
    public void startDevice() {
        try {
            if(queuePower == null) this.queuePower = new ArrayBlockingQueue<>(SIZE_BUFFER_POWER_SENSOR);
            if(queueVoltage == null) this.queueVoltage = new ArrayBlockingQueue<>(SIZE_BUFFER_VOLTAGE_SENSOR);
            if(queueAmperage == null) this.queueAmperage = new ArrayBlockingQueue<>(SIZE_BUFFER_AMPERAGE_SENSOR);

            pool = Executors.newFixedThreadPool(AMOUNT_THREAD);
            pool.execute(getTask(minPower,maxPower,queuePower));
            pool.execute(getTask(minVoltage,maxVoltage,queueVoltage));
            pool.execute(getTask(minAmperage,maxAmperage,queueAmperage));

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
            case POWER_SENSOR:
                return queuePower.take();
            case VOLTAGE_SENSOR:
                return queueVoltage.take();
            case AMPERAGE_SENSOR:
                return queueAmperage.take();
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
