package com.ssook.mvc.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageInfo {
    private int page;         // 현재 페이지
    private int perPage;      // 페이지당 개수
    private int totalCount;   // 전체 데이터 개수
    private int totalPages;   // 전체 페이지 수
    private boolean hasNext;  // 다음 페이지 유무
    private boolean hasPrev;  // 이전 페이지 유무
}