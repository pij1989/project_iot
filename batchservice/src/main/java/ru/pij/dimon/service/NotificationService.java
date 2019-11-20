package ru.pij.dimon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pij.dimon.pojo.Notification;
import ru.pij.dimon.repository.NotificationRepository;

import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional("ds2TransactionManager")
    public Date getNotificationDate(){
        if(notificationRepository.findNotification() != null){
            return notificationRepository.findNotification().getUpdateDate();
        }else {
            return null;
        }
    }

    @Transactional("ds2TransactionManager")
    public void updateNotificationDate(Date date){
        Notification notification = notificationRepository.findNotification();
        if (notification == null) {
            Notification newNotification = new Notification();
            newNotification.setId(1L);
            newNotification.setUpdateDate(date);
            notificationRepository.saveNotification(newNotification);
        } else {
            notification.setUpdateDate(date);
            notificationRepository.updateNotification(notification);
        }

    }
}
