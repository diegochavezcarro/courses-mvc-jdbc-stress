package com.diegochavez.courses.model;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        String code,
        String message,
        OffsetDateTime timestamp,
        String path
) {
}
