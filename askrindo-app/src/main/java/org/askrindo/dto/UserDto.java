package org.askrindo.dto;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private String id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private boolean isActive;

}

