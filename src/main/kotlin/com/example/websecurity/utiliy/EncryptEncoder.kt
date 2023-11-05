package com.example.websecurity.utiliy

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class EncryptEncoder(
    @Value("\${security.encoderSecret}") private val encoderSecret: String
) {

    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    fun encryptString(target: String): String = String(
        encoder.encode(
            cipherPkcs5(Cipher.ENCRYPT_MODE, encoderSecret).doFinal(target.toByteArray(Charsets.UTF_8))
        )
    )

    fun decryptString(target: String): String = String(
        cipherPkcs5(Cipher.DECRYPT_MODE, encoderSecret).doFinal(
            decoder.decode(target.toByteArray(Charsets.UTF_8))
        )
    )

    private fun cipherPkcs5(opMode: Int, secretKey: String): Cipher {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opMode, sk, iv)
        return c
    }
}