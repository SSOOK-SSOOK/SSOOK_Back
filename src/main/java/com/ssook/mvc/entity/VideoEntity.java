package com.ssook.mvc.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoEntity extends BaseEntity{
    // PK
    private Long videoId;

    // FK
    private Long categoryId;

    private String title;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer viewCount;

}
