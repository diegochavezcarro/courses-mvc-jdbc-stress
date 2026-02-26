package com.diegochavez.courses.service;

import static org.mockito.Mockito.when;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.repository.CourseReactiveRepository;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CourseServiceIntegrationTest {

    @Mock
    private CourseReactiveRepository courseReactiveRepository;

    @Test
    void shouldPreserveOrderAndLimitFromRepositoryFlow() {
        CourseService courseService = new CourseService(courseReactiveRepository);

        Course first = new Course(1L, "JAVA-101", "Java Basics", "Desc", "BEGINNER", 20, true, OffsetDateTime.now());
        Course second = new Course(2L, "JAVA-201", "Java Intermediate", "Desc", "INTERMEDIATE", 30, true, OffsetDateTime.now());

        when(courseReactiveRepository.findAll(2)).thenReturn(Flux.just(first, second));

        StepVerifier.create(courseService.getCourses(2))
                .expectNext(first)
                .expectNext(second)
                .verifyComplete();
    }
}
