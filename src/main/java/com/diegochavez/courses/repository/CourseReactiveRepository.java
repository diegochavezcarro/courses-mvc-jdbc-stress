package com.diegochavez.courses.repository;

import com.diegochavez.courses.model.Course;
import reactor.core.publisher.Flux;

public interface CourseReactiveRepository {

    Flux<Course> findAll(int limit);
}
