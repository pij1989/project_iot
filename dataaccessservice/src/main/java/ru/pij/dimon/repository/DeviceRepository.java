package ru.pij.dimon.repository;

import org.hibernate.SessionFactory;
import ru.pij.dimon.pojo.Device;

import java.util.List;

public class DeviceRepository extends BaseRepository<Device> {

    public DeviceRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Device> findDevices(int count){
        return sessionFactory.getCurrentSession()
                .createQuery("from Device",Device.class)
                .setMaxResults(count)
                .list();
    }

    public List<Device> findDeviceByType(String type){
        return sessionFactory.getCurrentSession()
                .createQuery("select d from Device d where d.type like :param",Device.class)
                .setParameter("param","%"+type+"%")
                .list();
    }
}
