package ru.pij.dimon.repository;

import org.hibernate.SessionFactory;
import ru.pij.dimon.pojo.Role;
import ru.pij.dimon.pojo.RoleName;

public class RoleRepository extends BaseRepository<Role> {

    public RoleRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Role findRoleByName(RoleName roleName){
        return sessionFactory.getCurrentSession().createQuery("from Role r where r.roleName = :param",Role.class)
                .setParameter("param",roleName)
                .getResultStream()
                .findFirst()
                .orElseThrow();
    }
}
