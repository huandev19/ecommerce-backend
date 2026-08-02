package com.v8n.modules.identity.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "customer_addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @jakarta.persistence.Transient
    private String label;

    @jakarta.persistence.Transient
    private String recipientName;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "address_1", nullable = false, length = 255)
    private String street;

    @Column(name = "address_2", length = 255)
    private String ward;

    @jakarta.persistence.Transient
    private String district;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "province", length = 100)
    private String state;

    @Column(name = "country_code", nullable = false, length = 2)
    private String country;

    @Column(name = "postal_code", length = 50)
    private String zipCode;

    @Column(name = "is_default_shipping", nullable = false)
    private boolean defaultShipping = false;

    @Column(name = "is_default_billing", nullable = false)
    private boolean defaultBilling = false;

    @jakarta.persistence.Transient
    private String addressType;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "company", length = 255)
    private String company;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;

    public Customer getLinkedCustomer() {
        return customer;
    }

    public void setLinkedCustomer(Customer customer) {
        this.customer = customer;
    }
}
