package com.example.websecurity.test.controller

import com.example.websecurity.test.service.TestService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc


@WebMvcTest(TestController::class)
class TestControllerTest {
    private lateinit var mockMvc: MockMvc
    private lateinit var testController: TestController
    @MockBean
    private lateinit var testService: TestService
    private lateinit var mapper: ObjectMapper

    companion object {
        private const val url = "api/v1/test"
    }

}