package com.diegochavez.courses.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diegochavez.courses.model.Course;
import java.time.OffsetDateTime;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class CourseReactiveRepositoryContractTest {

    @SuppressWarnings({"null", "unchecked"})
    @Test
    void shouldImplementReactiveRepositoryContract() {
        DatabaseClient databaseClient = mock(DatabaseClient.class);
        DatabaseClient.GenericExecuteSpec executeSpec = mock(DatabaseClient.GenericExecuteSpec.class);
        RowsFetchSpec<Course> rowsFetchSpec = mock(RowsFetchSpec.class);

        when(databaseClient.sql(anyString())).thenReturn(executeSpec);
        when(executeSpec.bind(anyInt(), any())).thenReturn(executeSpec);
        when(executeSpec.map(any(BiFunction.class))).thenReturn(rowsFetchSpec);

        Course expected = new Course(1L, "JAVA-101", "Java Basics", "Desc", "BEGINNER", 20, true, OffsetDateTime.now());
        when(rowsFetchSpec.all()).thenReturn(Flux.just(expected));

        CourseRepository repository = new CourseRepository(databaseClient, new CourseRowMapper());

        assertThat(repository).isInstanceOf(CourseReactiveRepository.class);
        StepVerifier.create(repository.findAll(1))
                .expectNext(expected)
                .verifyComplete();
    }
}
