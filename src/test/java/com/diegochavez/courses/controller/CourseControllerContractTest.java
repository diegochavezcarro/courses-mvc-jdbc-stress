package com.diegochavez.courses.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.service.CourseQueryService;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(controllers = CourseController.class)
@Import(GlobalExceptionHandler.class)
class CourseControllerContractTest {

    private final WebTestClient webTestClient;

    CourseControllerContractTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @MockitoBean
    private CourseQueryService courseQueryService;

    @Test
    void shouldReturnCoursesWithCompatibleShape() {
        Course course = new Course(
                1L,
                "JAVA-101",
                "Java Basics",
                "Intro course",
                "BEGINNER",
                20,
                true,
                OffsetDateTime.now()
        );

        when(courseQueryService.getCourses(anyInt())).thenReturn(Flux.just(course));

        webTestClient.get()
                .uri("/courses?limit=100")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].code").isEqualTo("JAVA-101")
                .jsonPath("$[0].title").isEqualTo("Java Basics");
    }
}
