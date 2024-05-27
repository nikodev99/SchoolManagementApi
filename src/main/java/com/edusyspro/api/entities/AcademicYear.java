package com.edusyspro.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdate;

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;


}
