package com.edusyspro.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "planning_id", referencedColumnName = "id")
    @JsonIgnore
    private Planning semester;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    @JsonIgnore
    private Exam exam;

    private String preparedBy;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClasseEntity classeEntity;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course subject;

    private String examName;

    private LocalDate examDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Score> marks;
}
