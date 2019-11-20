package ru.pij.dimon.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk", sequenceName = "role_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pk")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="role_name")
    private RoleName roleName;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                getRoleName() == role.getRoleName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName());
    }
}
