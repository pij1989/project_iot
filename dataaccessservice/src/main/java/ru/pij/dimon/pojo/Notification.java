package ru.pij.dimon.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Notification implements Serializable {

    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
