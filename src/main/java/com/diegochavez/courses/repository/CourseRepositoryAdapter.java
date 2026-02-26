package com.diegochavez.courses.repository;

import com.diegochavez.courses.model.Course;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Primary
public class CourseRepositoryAdapter implements CourseReactiveRepository {

    private final CourseRepository delegate;

    public CourseRepositoryAdapter(CourseRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Flux<Course> findAll(int limit) {
        return delegate.findAll(limit);
    }
}
