package com.diegochavez.courses.model;

import java.time.OffsetDateTime;

public record Course(
        Long id,
        String code,
        String title,
        String description,
        String level,
        Integer durationH,
        Boolean active,
        OffsetDateTime createdAt
) {
}
