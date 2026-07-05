package com.p_school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")

// ====================== CLASS LEVEL SECURITY ======================
// Is class ke andar jitni bhi APIs hain, sabse pehle ye condition check hogi.
// Agar user ke paas ROLE_ADMIN nahi hai to kisi bhi API tak access nahi milega.
//
// Note:
// hasRole("ADMIN") => Spring internally "ROLE_ADMIN" check karta hai.
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    // ================================================================
    // 1. Koi Security Nahi
    // Sab authenticated users access kar sakte hain.
    // ================================================================

//    @PostMapping
//    public ResponseEntity<String> updateUser() {
//        return ResponseEntity.ok("Update User");
//    }


    // ================================================================
    // 2. Sirf Permission Check
    // User ke paas USER_UPDATE authority honi chahiye.
    // ================================================================

//    @PostMapping
//    @PreAuthorize("hasAuthority('USER_UPDATE')")
//    public ResponseEntity<String> updateUser() {
//        return ResponseEntity.ok("Update User");
//    }


    // ================================================================
    // 3. Role + Permission (AND)
//    Dono condition true honi chahiye.
//
//    ROLE_ADMIN
//          AND
//    USER_UPDATE
//
//    Agar ek bhi missing hai to 403 Forbidden milega.
//    ================================================================

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN') and hasAuthority('USER_UPDATE')")
//    public ResponseEntity<String> updateUser() {
//        return ResponseEntity.ok("Update User");
//    }


    // ================================================================
    // 4. Role OR Permission
//
//    ROLE_ADMIN
//           OR
//    USER_UPDATE
//
//    Dono me se koi bhi ek condition true hai to access mil jayega.
//    ================================================================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_UPDATE')")
    public ResponseEntity<String> updateUser() {
        return ResponseEntity.ok("Update User");
    }


    // ================================================================
    // 5. Multiple Roles
//
//    ADMIN ya SUPER_ADMIN me se koi bhi role chalega.
//    ================================================================

//    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")


    // ================================================================
    // 6. Multiple Permissions
//
//    USER_CREATE
//    USER_UPDATE
//    USER_DELETE
//
//    Inme se koi bhi ek permission hogi to access mil jayega.
//    ================================================================

//    @PreAuthorize("hasAnyAuthority('USER_CREATE','USER_UPDATE','USER_DELETE')")


    // ================================================================
    // 7. Complex Expression
//
//    ADMIN role bhi hona chahiye
//    Aur CREATE ya UPDATE me se koi ek permission bhi honi chahiye.
//    ================================================================

//    @PreAuthorize("hasRole('ADMIN') and hasAnyAuthority('USER_CREATE','USER_UPDATE')")


    // ================================================================
    // IMPORTANT INTERVIEW POINT
//
//    Class Level + Method Level dono lage ho to:
//
//    Class Level:
//        hasRole('ADMIN')
//
//    Method Level:
//        hasAuthority('USER_UPDATE')
//
//    Final Check:
//
//        ROLE_ADMIN
//             AND
//        USER_UPDATE
//
//    Matlab dono pass karne padenge.
//    ================================================================
}