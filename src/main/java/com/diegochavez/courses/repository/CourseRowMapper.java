package com.diegochavez.courses.repository;

import com.diegochavez.courses.model.Course;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.OffsetDateTime;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
public class CourseRowMapper implements BiFunction<Row, RowMetadata, Course> {

    @Override
    public Course apply(Row row, RowMetadata rowMetadata) {
        return new Course(
                row.get("id", Long.class),
                row.get("code", String.class),
                row.get("title", String.class),
                row.get("description", String.class),
                row.get("level", String.class),
                row.get("duration_h", Integer.class),
                row.get("active", Boolean.class),
                row.get("created_at", OffsetDateTime.class)
        );
    }
}
