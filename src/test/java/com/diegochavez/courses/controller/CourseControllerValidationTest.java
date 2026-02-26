package com.diegochavez.courses.controller;

import com.diegochavez.courses.service.CourseQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = CourseController.class)
@Import(GlobalExceptionHandler.class)
class CourseControllerValidationTest {

    private final WebTestClient webTestClient;

    @Autowired
    CourseControllerValidationTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @MockBean
    private CourseQueryService courseQueryService;

    @Test
    void shouldRejectLimitLessOrEqualZero() {
        webTestClient.get()
                .uri("/courses?limit=0")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").exists()
                .jsonPath("$.message").exists()
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.path").isEqualTo("/courses");
    }

    @Test
    void shouldRejectLimitGreaterThanMax() {
        webTestClient.get()
                .uri("/courses?limit=1001")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").exists()
                .jsonPath("$.message").exists()
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.path").isEqualTo("/courses");
    }
}
