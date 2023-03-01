package org.bbaemin.user.controller.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
