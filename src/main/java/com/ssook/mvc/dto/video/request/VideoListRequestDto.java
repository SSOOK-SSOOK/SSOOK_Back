package com.ssook.mvc.dto.video.request;

import lombok.Data;

@Data
public class VideoListRequestDto {
    private String categoryName; // 카테고리 명
    private Integer sortedType; // 정렬기준(1: 최신순, 2: 조회수순)
    private int page = 1; // 페이지 번호
    private int size = 12; // 페이지당 개수

    // SQL LIMIT에서 사용할 offset
    public int getOffset() {
        return (page - 1) * size;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSortedType() {
        return sortedType;
    }

    public void setSortedType(Integer sortedType) {
        this.sortedType = sortedType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
