package com.ssook.mvc.controller;

import com.ssook.mvc.dto.UserDto;
import com.ssook.mvc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "회원 관리 API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "중복 검사 및 암호화를 포함한 회원가입")
    // @Valid: DTO에 붙인 @NotBlank 같은 조건을 검사함
    public ResponseEntity<String> signup(@Valid @RequestBody UserDto userDto) {
        try {
            userService.signup(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            // 중복된 이메일/닉네임일 경우 400 에러와 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }
    }
}