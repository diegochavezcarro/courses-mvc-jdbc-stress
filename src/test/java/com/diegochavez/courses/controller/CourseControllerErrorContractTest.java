package com.diegochavez.courses.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.diegochavez.courses.service.CourseQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(controllers = CourseController.class)
@Import(GlobalExceptionHandler.class)
class CourseControllerErrorContractTest {

    private final WebTestClient webTestClient;

    @Autowired
    CourseControllerErrorContractTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @MockitoBean
    private CourseQueryService courseQueryService;

    @Test
    void shouldReturnStandardizedPayloadForUnexpectedErrors() {
        when(courseQueryService.getCourses(anyInt())).thenReturn(Flux.error(new RuntimeException("boom")));

        webTestClient.get()
                .uri("/courses?limit=100")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.code").isEqualTo("INTERNAL_ERROR")
                .jsonPath("$.message").exists()
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.path").isEqualTo("/courses");
    }
}
