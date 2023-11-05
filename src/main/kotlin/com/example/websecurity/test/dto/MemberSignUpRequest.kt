package com.example.websecurity.test.dto

import com.example.websecurity.status.Gender
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class MemberSignUpRequest(
    @JsonProperty("loginId")
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String,
)

data class MemberSignInRequest(
    val loginId: String,
    val password: String,
)

data class MemberDtoResponse(
    val id: Long,
    val loginId: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val email: String
)