package com.cakeshop.api_main.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtil {
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof Jwt jwt) {
            System.out.println("JWT Claims: " + jwt.getClaims());
            String username = jwt.getClaimAsString("sub");
            System.out.println("Claim 'name': " + username);
            return username;
        } else {
            return principal.toString();
        }
    }
}
