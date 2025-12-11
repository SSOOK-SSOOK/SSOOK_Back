package com.ssook.mvc.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity extends BaseEntity{
    // PK
    private Long commentId;

    // FK
    private Long videoId;
    private Long userId;
    private Long parentId; // 대댓글 용

    private String content;
    private boolean isDeleted = false;
}
