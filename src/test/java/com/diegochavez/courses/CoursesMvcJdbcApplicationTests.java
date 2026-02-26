package com.diegochavez.courses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
class CoursesMvcJdbcApplicationTests {

    @Test
    void applicationClassShouldBePresent() {
        assertThat(CoursesMvcJdbcApplication.class).isNotNull();
    }
}
