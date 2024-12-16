package org.askrindo.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_lookup")
@Data
public class MasterLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "lookup_key", nullable = false)
    private String lookupKey;

    @Column(name = "lookup_group", nullable = false)
    private String lookupGroup;

    @Column(name = "key_only", nullable = false)
    private String keyOnly;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "version")
    private Integer version;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}

