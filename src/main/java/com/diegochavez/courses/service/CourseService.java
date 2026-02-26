package com.diegochavez.courses.service;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.repository.CourseReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CourseService implements CourseQueryService {

    private final CourseReactiveRepository courseRepository;

    public CourseService(CourseReactiveRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Flux<Course> getCourses(int limit) {
        return courseRepository.findAll(limit);
    }
}
