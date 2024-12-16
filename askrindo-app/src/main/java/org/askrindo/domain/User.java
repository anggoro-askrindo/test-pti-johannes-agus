package org.askrindo.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_user")
@Data
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id_user", nullable = false, updatable = false)
    private String id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "fullname", nullable = false, length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

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

