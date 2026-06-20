package com.v8n.fulfillment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentRequest {
    private String trackingNumber;
    private String carrier;
}