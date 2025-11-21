package com.ssook.mvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "회원 가입 요청 데이터")
public class UserDto {

    @Schema(hidden = true)
    private Long userId;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "로그인 이메일", example = "ssafy@ssook.com")
    private String email;

    @NotBlank
    @Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다.")
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Schema(description = "닉네임", example = "쑥쑥이")
    private String nickname;
    
    // ... 나머지 필드 동일
    @Schema(hidden = true)
    private String role;
    private String intro;
    @Schema(hidden = true)
    private LocalDateTime createdAt;
    @Schema(hidden = true)
    private LocalDateTime updatedAt;
}