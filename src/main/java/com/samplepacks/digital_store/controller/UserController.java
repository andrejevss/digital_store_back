package com.samplepacks.digital_store.controller;

import com.samplepacks.digital_store.entity.LocalUser;
import com.samplepacks.digital_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<LocalUser> getUserProfile(Principal principal) {
        return ResponseEntity.ok(
                (LocalUser) userService.getAllUsers()
        );
    }
}
