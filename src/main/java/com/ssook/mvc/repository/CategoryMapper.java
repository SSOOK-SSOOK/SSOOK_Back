package com.ssook.mvc.repository;

import com.ssook.mvc.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CategoryMapper {
    // 목록 조회 (페이징 적용)
    List<CategoryEntity> selectCategoryList(@Param("offset") int offset, @Param("limit") int limit);

    // 전체 개수 (페이징 계산용)
    int countCategory();
}