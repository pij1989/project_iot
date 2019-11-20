package ru.pij.dimon.repository;

import org.hibernate.SessionFactory;
import ru.pij.dimon.pojo.User;

public class UserRepository extends BaseRepository<User> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findUserByLogin(String login){
        return sessionFactory.getCurrentSession().createQuery("from User u where u.login = :param",User.class)
                .setParameter("param",login)
                .getResultStream()
                .findFirst()
                .orElseThrow();
    }
}
