package com.example.websecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebSecurityApplication

fun main(args: Array<String>) {
    runApplication<WebSecurityApplication>(*args)
}
