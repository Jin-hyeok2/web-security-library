package com.example.websecurity.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security.policy")
class SecurityPolicy {
    lateinit var permitAllUri : Array<String>
    lateinit var servletPrefix: String
}