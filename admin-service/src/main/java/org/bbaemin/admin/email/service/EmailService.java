package org.bbaemin.admin.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(String userEmail, String content) {

        log.info("************************* Send Email *************************");
        log.info(">>>>> [메일 전송] email : {}, content : {}", userEmail, content);
        log.info("**************************************************************");
    }
}
