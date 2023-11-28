package com.mszgajewski.NotesOnline.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "create_dt", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    @Column(name = "update_dt", nullable = false)
    private Date updateDate;
}