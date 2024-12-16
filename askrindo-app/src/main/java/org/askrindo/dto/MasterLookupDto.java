package org.askrindo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterLookupDto {

    private String id;

    private String lookupKey;

    private String lookupGroup;

    private String keyOnly;

    private String label;

    private Boolean isActive;

    private Integer version;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;

}
