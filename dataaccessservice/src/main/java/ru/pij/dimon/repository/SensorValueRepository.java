package ru.pij.dimon.repository;

import org.hibernate.SessionFactory;
import ru.pij.dimon.pojo.SensorValue;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class SensorValueRepository extends BaseRepository<SensorValue> {


    public SensorValueRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<SensorValue> findByDateInterval(Long id, Date fromDate, Date toDate){
        return sessionFactory.getCurrentSession()
                .createQuery("select v from SensorValue v where v.sensor.id = :param1 and v.date between :param2 and :param3",SensorValue.class)
                .setParameter("param1",id)
                .setParameter("param2",fromDate, TemporalType.TIMESTAMP)
                .setParameter("param3",toDate,TemporalType.TIMESTAMP)
                .list();
    }
}
