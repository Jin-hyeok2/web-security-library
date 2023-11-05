package com.example.websecurity.test.controller

import com.example.websecurity.test.dto.MemberDtoResponse
import com.example.websecurity.test.dto.MemberSignInRequest
import com.example.websecurity.test.dto.MemberSignUpRequest
import com.example.websecurity.test.service.TestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/member")
class TestController(
    private val testService: TestService
) {

    //    @GetMapping
//    fun test(): ResponseEntity<String> {
//        return ResponseEntity.ok(testService.test("용식이"))
//    }
    @PostMapping
    fun signUp(@RequestBody memberSignUpRequest: MemberSignUpRequest): ResponseEntity<String> {
        return ResponseEntity.ok(testService.signUp(memberSignUpRequest))
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: MemberSignInRequest): ResponseEntity<CustomResponse> {
        return ResponseEntity.ok(
            CustomResponse(
                testService.signIn(request.loginId, request.password)
            )
        )
    }

    @GetMapping("/info/{id}")
    fun searchMyInfo(@PathVariable id: Long): ResponseEntity<MemberDtoResponse> {
        return ResponseEntity.ok(testService.searchMyInfo(id))
    }
}

data class CustomResponse(
    val data: Any,
)