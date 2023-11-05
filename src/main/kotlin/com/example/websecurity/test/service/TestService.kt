package com.example.websecurity.test.service

import com.example.websecurity.status.Role
import com.example.websecurity.test.dto.MemberDtoResponse
import com.example.websecurity.test.dto.MemberSignUpRequest
import com.example.websecurity.test.entity.MemberEntity
import com.example.websecurity.test.entity.MemberRoleEntity
import com.example.websecurity.test.repository.MemberRepository
import com.example.websecurity.test.repository.MemberRoleRepository
import com.example.websecurity.utiliy.JwtUtility
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.InvalidParameterException

@Service
class TestService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtUtility: JwtUtility,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(memberSignUpRequest: MemberSignUpRequest): String {
        var member: MemberEntity? = memberRepository.findByLoginId(memberSignUpRequest.loginId)
        if (member != null) {
            return "이미 등록된 ID 입니다"
        }
        member = MemberEntity(
            loginId = memberSignUpRequest.loginId,
            password = passwordEncoder.encode(memberSignUpRequest.password),
            name = memberSignUpRequest.name,
            birthDate = memberSignUpRequest.birthDate,
            gender = memberSignUpRequest.gender,
            email = memberSignUpRequest.email
        )

        member = memberRepository.save(member)
        val memberRole: MemberRoleEntity = MemberRoleEntity(
            role = Role.ROLE_ADMIN, memberEntity = member
        )
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    fun signIn(loginId: String, password: String): String {
        val member: MemberEntity =
            memberRepository.findByLoginId(loginId) ?: throw RuntimeException("해당 id로 등록된 계정이 없습니다")
        val authenticationToken = UsernamePasswordAuthenticationToken(loginId, password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtUtility.createAccessToken(authentication)
    }

    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: MemberEntity =
            memberRepository.findByIdOrNull(id) ?: throw InvalidParameterException("잘못 된 id 입력입니다")
        return member.toDto()
    }
}