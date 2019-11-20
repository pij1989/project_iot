package ru.pij.dimon.repository;

import org.hibernate.SessionFactory;
import ru.pij.dimon.pojo.Sensor;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class SensorRepository extends BaseRepository<Sensor> {

    public SensorRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Sensor> findSensorsByType(String type){
        return sessionFactory.getCurrentSession()
                .createQuery("select s from Sensor s where s.type like :param",Sensor.class)
                .setParameter("param","%"+type+"%")
                .list();
    }

    public List<Sensor> findByDate(Long id,Date fromDate, Date toDate){
        return sessionFactory.getCurrentSession()
                .createQuery("select distinct s from Sensor s join s.values v on s.id = :param1 where v.date between :param2 and :param3",Sensor.class)
                .setParameter("param1",id)
                .setParameter("param2",fromDate, TemporalType.TIMESTAMP)
                .setParameter("param3",toDate,TemporalType.TIMESTAMP)
                .list();
    }
}
