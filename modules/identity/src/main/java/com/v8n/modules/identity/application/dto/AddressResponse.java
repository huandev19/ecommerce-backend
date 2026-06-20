package com.v8n.modules.identity.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
    private UUID id;
    private String label;
    private String recipientName;
    private String phone;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private boolean defaultShipping;
    private boolean defaultBilling;
    private String addressType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}