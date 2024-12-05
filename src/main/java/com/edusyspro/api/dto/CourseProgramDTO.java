package com.edusyspro.api.dto;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgramDTO {
    private Long id;
    private String topic;
    private String purpose;
    private String description;
    private boolean active;
    private boolean passed;
    private LocalDate updateDate;
    private Semester semester;
    private CourseDTO course;
    private ClasseEntity classe;
    private Teacher teacher;
}
