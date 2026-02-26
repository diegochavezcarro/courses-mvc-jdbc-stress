package com.diegochavez.courses.repository;

import com.diegochavez.courses.model.Course;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.function.BiFunction;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CourseRepository implements CourseReactiveRepository {

    private static final String FIND_ALL_SQL = """
            SELECT id, code, title, description, level, duration_h, active, created_at
            FROM courses
            ORDER BY id
            LIMIT ?
            """;

    private final DatabaseClient databaseClient;
    private final BiFunction<Row, RowMetadata, Course> courseRowMapper;

    public CourseRepository(DatabaseClient databaseClient, CourseRowMapper courseRowMapper) {
        this.databaseClient = databaseClient;
        this.courseRowMapper = courseRowMapper;
    }

    @Override
    public Flux<Course> findAll(int limit) {
        return databaseClient.sql(FIND_ALL_SQL)
                .bind(0, limit)
                .map((row, metadata) -> courseRowMapper.apply(row, metadata))
                .all();
    }
}
