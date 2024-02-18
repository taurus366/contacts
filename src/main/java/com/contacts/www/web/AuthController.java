package com.contacts.www.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/")
public class AuthController {

    @GetMapping("status")
    public ResponseEntity<?> checkAuthStatus(HttpServletRequest request) {

        boolean isAuthenticated = request.getUserPrincipal() != null;

       return !isAuthenticated ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
               : ResponseEntity.status(HttpStatus.OK).build();
    }
}
