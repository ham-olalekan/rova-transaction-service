package com.rova.transactionservice.dals;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.UUID;

import static com.rova.transactionservice.util.Constants.IAPPENDABLE_REF_SEPARATOR;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(length = 64, nullable = false, updatable = false, unique = true)
    private String reference;

    @PrePersist
    public void appendReference() {
        if (!StringUtils.hasText(this.reference)) {
            this.reference = UUID.randomUUID().toString().replaceAll("-", "");
        }
    }

    public String getReference() {
        return String.format("%s%s%s", this.id, IAPPENDABLE_REF_SEPARATOR, this.reference);
    }
}
