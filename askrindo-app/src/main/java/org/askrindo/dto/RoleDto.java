package org.askrindo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class RoleDto {

    @NotBlank(message = "Nama role tidak boleh kosong")
    private String roleName;

    @NotBlank(message = "Keterangan tidak boleh kosong")
    private String keterangan;

    private boolean isActive = true;

}

