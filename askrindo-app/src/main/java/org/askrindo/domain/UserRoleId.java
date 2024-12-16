package org.askrindo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class UserRoleId implements Serializable {

    @Column(name = "id_user")
    private UUID idUser;

    @Column(name = "role_name", length = 20)
    private String roleName;
}
