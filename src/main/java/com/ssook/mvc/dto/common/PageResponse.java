package com.ssook.mvc.dto.common;

import java.util.List;
import lombok.Getter;

@Getter
public class PageResponse<T> {
    private List<T> content;
    private PageInfo pageInfo;

    public PageResponse(List<T> content, int page, int size, int totalCount) {
        this.content = content;
        
        // 총 페이지 수 계산 (나눗셈 올림 처리)
        int totalPages = (int) Math.ceil((double) totalCount / size);

        this.pageInfo = PageInfo.builder()
                .page(page)
                .perPage(size)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .hasNext(page < totalPages)
                .hasPrev(page > 1)
                .build();
    }
}