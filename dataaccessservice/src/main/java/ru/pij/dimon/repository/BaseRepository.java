package ru.pij.dimon.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseRepository<T> {

    protected SessionFactory sessionFactory;
    private Class<T> aClass;

    @SuppressWarnings("unchecked")
    public BaseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        aClass =((Class<T>)((ParameterizedType)getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void save (T entity){
        sessionFactory.getCurrentSession().persist(entity);
    }

    public T findById(Serializable id){
        return sessionFactory.getCurrentSession().get(aClass,id);
    }

    public void update(T entity){
        sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(T entity){
        sessionFactory.getCurrentSession().delete(entity);
    }

    public List<T> findAll(){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(aClass);
        return session.createQuery(cq.select(cq.from(aClass))).getResultList();
    }
}
