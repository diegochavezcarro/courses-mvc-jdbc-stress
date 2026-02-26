package com.diegochavez.courses.controller;

import com.diegochavez.courses.model.Course;
import com.diegochavez.courses.service.CourseQueryService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Validated
@RequestMapping("/courses")
public class CourseController {

    private final CourseQueryService courseService;

    public CourseController(CourseQueryService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Flux<Course> getCourses(
            @RequestParam(defaultValue = "100")
            @Min(value = 1, message = "limit must be greater than 0")
            @Max(value = 1000, message = "limit must be less than or equal to 1000")
            int limit
    ) {
        return courseService.getCourses(limit);
    }
}
