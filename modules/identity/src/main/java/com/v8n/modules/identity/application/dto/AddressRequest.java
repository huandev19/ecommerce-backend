package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequest {

    @Size(max = 50, message = "Label must not exceed 50 characters")
    private String label;

    @NotBlank(message = "Recipient name is required")
    @Size(max = 255, message = "Recipient name must not exceed 255 characters")
    private String recipientName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Street address is required")
    @Size(max = 500, message = "Street must not exceed 500 characters")
    private String street;

    @Size(max = 100, message = "Ward must not exceed 100 characters")
    private String ward;

    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District must not exceed 100 characters")
    private String district;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Size(max = 20, message = "Zip code must not exceed 20 characters")
    private String zipCode;

    private Boolean defaultShipping;

    private Boolean defaultBilling;

    @Size(max = 20, message = "Address type must not exceed 20 characters")
    private String addressType;
}