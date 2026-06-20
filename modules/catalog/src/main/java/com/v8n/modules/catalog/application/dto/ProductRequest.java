package com.v8n.modules.catalog.application.dto;

import com.v8n.modules.catalog.domain.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String title;
    private String slug;
    private String subtitle;
    private String description;
    private String thumbnailUrl;
    private ProductStatus status;
    private UUID categoryId;
    private String originCountry;
    private boolean discountable = true;
    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer length;
    private String hsCode;
    private String material;
}