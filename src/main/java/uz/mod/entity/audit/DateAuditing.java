package uz.mod.entity.audit;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAuditing {
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;


    @UpdateTimestamp
    private Timestamp updatedAt;
}
