package com.ssook.mvc.entity;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 수정일시
}
