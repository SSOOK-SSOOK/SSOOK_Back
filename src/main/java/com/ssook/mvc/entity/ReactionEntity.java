package com.ssook.mvc.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionEntity extends BaseEntity{
    // PK
    private Long reactionId;

    // FK
    private Long userId;
    private Long videoId;
}
