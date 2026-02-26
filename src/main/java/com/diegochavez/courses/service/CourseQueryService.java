package com.diegochavez.courses.service;

import com.diegochavez.courses.model.Course;

import reactor.core.publisher.Flux;

public interface CourseQueryService {

    Flux<Course> getCourses(int limit);
}
