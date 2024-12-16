package org.askrindo.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "r_user_roles")
@Data
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("idUser")
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @MapsId("roleName")
    @ManyToOne
    @JoinColumn(name = "role_name", nullable = false)
    private Role role;
}

