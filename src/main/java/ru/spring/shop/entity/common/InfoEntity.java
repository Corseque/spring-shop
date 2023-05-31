package ru.spring.shop.entity.common;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@MappedSuperclass
public class InfoEntity extends BaseEntity {

    @Version
    @Column(name = "version")
    private int version;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String created_by;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime created_date;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String last_modified_by;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime last_modified_date;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private Status status;

    public InfoEntity(Long id, int version, String created_by, LocalDateTime created_date, String last_modified_by,
                      LocalDateTime last_modified_date
//            , Status status
    ) {
        super(id);
        this.version = version;
        this.created_by = created_by;
        this.created_date = created_date;
        this.last_modified_by = last_modified_by;
        this.last_modified_date = last_modified_date;
//        this.status = status;
    }
}
