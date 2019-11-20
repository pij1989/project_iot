package ru.pij.dimon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.pij.dimon.pojo.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class NotificationRepository {

    @Qualifier("ds2EntityManagerFactory")
    @Autowired
    @PersistenceContext(unitName = "persistence_unit_2")
    private EntityManager entityManager;

    public Notification findNotification(){
        return entityManager.find(Notification.class,1L);
    }

    public void updateNotification(Notification notification){
        entityManager.merge(notification);
    }

    public void saveNotification(Notification notification){
        entityManager.persist(notification);
    }
}
