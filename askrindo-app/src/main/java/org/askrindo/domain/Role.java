package org.askrindo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_role")
@Data
public class Role {

    @Id
    @Column(name = "role_name", length = 20)
    private String roleName;

    @Column(name = "keterangan", nullable = false, length = 255)
    private String keterangan;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}

