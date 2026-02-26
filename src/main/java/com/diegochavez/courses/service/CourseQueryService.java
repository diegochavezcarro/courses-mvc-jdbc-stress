package com.diegochavez.courses.service;

import reactor.core.publisher.Flux;

public interface CourseQueryService {

    Flux<Course> getCourses(int limit);
}
