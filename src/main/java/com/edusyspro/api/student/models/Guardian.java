package com.edusyspro.api.student.models;

import com.edusyspro.api.entities.Address;
import com.edusyspro.api.entities.enums.Gender;
import com.edusyspro.api.entities.enums.Status;
import com.edusyspro.api.student.entities.StudentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guardian {
    private UUID id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private Status status;

    @JsonProperty("gender")
    private Gender genre;

    private String emailId;
    private String jobTitle;
    private String company;
    private String telephone;
    private String mobile;
    private Address address;
    private List<StudentEntity> studentEntity;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
