package com.diegochavez.courses.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CourseListRequest(
        @Min(value = 1, message = "limit must be greater than 0")
        @Max(value = 1000, message = "limit must be less than or equal to 1000")
        int limit
) {
}
