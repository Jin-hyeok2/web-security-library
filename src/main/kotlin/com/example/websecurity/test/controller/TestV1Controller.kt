package com.example.websecurity.test.controller

import com.example.websecurity.annotation.Admin
import com.example.websecurity.annotation.Member
import com.example.websecurity.dto.CustomUser
import com.example.websecurity.test.dto.MemberDtoResponse
import com.example.websecurity.test.service.TestService
import com.example.websecurity.utiliy.JwtUtility
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class TestV1Controller(
    private val testService: TestService,
) {
    @GetMapping("/info")
    @Admin
    fun searchMyInfo(): ResponseEntity<MemberDtoResponse> {
        val userId = JwtUtility.getUserId().toLong()
        return ResponseEntity.ok(testService.searchMyInfo(userId))
    }

}