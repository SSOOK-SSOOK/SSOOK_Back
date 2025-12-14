package com.ssook.mvc.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity extends BaseEntity {
    private Integer categoryId;    // DB: INT
    private Integer parentId;      // DB: INT (상위 카테고리)
    private String categoryName;   // DB: VARCHAR
    private String description;    // DB: VARCHAR
    private String imageUrl;       // DB: VARCHAR
}