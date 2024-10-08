package com.web.stard.domain.member.api;

import com.web.stard.domain.member.dto.EmailDto;
import com.web.stard.domain.member.application.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/verification-requests")
    public ResponseEntity sendMessage(@RequestBody EmailDto emailDto) throws Exception {
        emailService.sendCodeToEmail(emailDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verifications")
    public Boolean verificationEmail(@RequestParam(name = "email") String email, @RequestParam(name = "authCode") String authCode)  {
        try {
            return emailService.verifiedCode(email, authCode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
