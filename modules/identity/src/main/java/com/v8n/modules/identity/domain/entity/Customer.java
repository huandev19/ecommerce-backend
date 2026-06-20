package com.v8n.modules.identity.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @jakarta.persistence.Transient
    private User user;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "phone", length = 50)
    private String phone;

    @jakarta.persistence.Transient
    private String company;

    @Column(name = "has_account", nullable = false)
    private boolean hasAccount = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;

    public String getFullName() {
        if (firstName == null && lastName == null) return null;
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}
