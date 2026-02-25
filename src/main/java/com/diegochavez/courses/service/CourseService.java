package com.diegochavez.courses.service;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(int limit) {
        return courseRepository.findAll(limit);
    }
}
