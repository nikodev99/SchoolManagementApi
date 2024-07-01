package com.edusyspro.api.student.models.dtos;

import com.edusyspro.api.entities.enums.Gender;
import com.edusyspro.api.entities.enums.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrolledStudent {
    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String emailId;
    private LocalDate birthDate;
    private String birthCity;
    private String nationality;
    private String reference;
    private String image;
    private ZonedDateTime lastEnrolledDate;
    private String classe;
    private Section grade;
}
