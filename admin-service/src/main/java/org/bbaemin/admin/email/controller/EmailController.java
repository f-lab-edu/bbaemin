package org.bbaemin.admin.email.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.admin.email.service.EmailService;
import org.bbaemin.config.response.ApiResult;
import org.bbaemin.dto.request.SendEmailRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ApiResult<Void> send(@Validated @RequestBody SendEmailRequest sendEmailRequest) {
        emailService.sendEmail(sendEmailRequest.getUserEmail(), sendEmailRequest.getContent());
        return ApiResult.ok();
    }
}
