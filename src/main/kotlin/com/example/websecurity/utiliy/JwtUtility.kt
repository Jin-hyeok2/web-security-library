package com.example.websecurity.utiliy

import com.example.websecurity.config.JwtPolicy
import com.example.websecurity.dto.CustomUser
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtility(
    private val jwtPolicy: JwtPolicy,
    private val encryptEncoder: EncryptEncoder,
) {

    companion object {
        fun getUserId(): String {
            return (SecurityContextHolder.getContext().authentication.principal as CustomUser<*>).userId.toString()
        }
    }

    fun createAccessToken(
        authentication: Authentication
    ): String {
        return createToken(
            authentication,
            getEncodedAccessKey(),
            jwtPolicy.accessToken.expirationTimeMinute,
        )
    }

    fun createRefreshToken(
        authentication: Authentication
    ): String {
        return createToken(
            authentication,
            getEncodedRefreshKey(),
            jwtPolicy.refreshToken.expirationTimeMinute,
        )
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token, getEncodedAccessKey())

        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val authorities = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails =
            CustomUser(userId.toString(), claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateAccessToken(token: String): Boolean {
        return validateToken(token, getEncodedAccessKey())
    }

    private fun validateToken(token: String, secretKey: String): Boolean {
        return try {
            getClaims(token, secretKey)
            true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}
                is MalformedJwtException -> {}
                is ExpiredJwtException -> {}
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}
            }
            println(e.message)
            false
        }
    }

    fun getClaims(token: String, secretKey: String): Claims {
        val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token).body
    }

    private fun createToken(
        authentication: Authentication,
        secretKey: String,
        expirationTime: Long,
    ): String {
        val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

        val claims: Claims = Jwts.claims()
        claims.subject = authentication.name
        claims["auth"] =
            authentication.authorities.joinToString(",", transform = GrantedAuthority::getAuthority)
        claims["userId"] =
            (authentication.principal as CustomUser<*>).userId
        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expirationTime * 60 * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getEncodedAccessKey(): String {
        return encryptEncoder.encryptString(jwtPolicy.accessToken.secretKey)
    }

    private fun getEncodedRefreshKey(): String {
        return encryptEncoder.encryptString(jwtPolicy.refreshToken.secretKey)
    }
}