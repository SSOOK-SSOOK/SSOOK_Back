package com.ssook.mvc.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum UserProfile {
    // 1단계에서 넣은 파일명에 맞춰 경로를 적어줍니다.
    IMAGE_1("/images/profile1.png"),
    IMAGE_2("/images/profile2.png"),
    IMAGE_3("/images/profile3.png"),
    IMAGE_4("/images/profile4.png"),
    IMAGE_5("/images/profile5.png"),
    IMAGE_6("/images/profile6.png"),
    IMAGE_7("/images/profile7.png"),
    IMAGE_8("/images/profile8.png"),
    DEFAULT("/images/profile9.png"); // 기본 이미지

    private final String url;

    // URL이 유효한지(우리 목록에 있는지) 검사
    public static boolean isValidUrl(String url) {
        return Arrays.stream(values())
                .anyMatch(profile -> profile.url.equals(url));
    }

    // 전체 이미지 경로 리스트 반환 (프론트엔드에 줄 목록)
    public static List<String> getAllUrls() {
        return Arrays.stream(values())
                .map(UserProfile::getUrl)
                .collect(Collectors.toList());
    }
}