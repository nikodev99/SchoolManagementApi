package com.edusyspro.api.student.entities;

import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "enrollment")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClasseEntity classe;

    private ZonedDateTime enrollmentDate;

    private boolean isArchived;

    @PrePersist
    public void onCreate() {
        enrollmentDate = Datetime.brazzavilleDatetime();
    }
}
