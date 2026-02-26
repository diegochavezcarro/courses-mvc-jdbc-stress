package com.diegochavez.courses.service;

import static org.mockito.Mockito.when;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.repository.CourseReactiveRepository;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CourseServiceUnitTest {

    @Mock
    private CourseReactiveRepository courseReactiveRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldDelegateCourseQueryToRepository() {
        Course course = new Course(1L, "JAVA-101", "Java Basics", "Desc", "BEGINNER", 20, true, OffsetDateTime.now());
        when(courseReactiveRepository.findAll(100)).thenReturn(Flux.just(course));

        StepVerifier.create(courseService.getCourses(100))
                .expectNext(course)
                .verifyComplete();
    }
}
