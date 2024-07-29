package com.edusyspro.api.student.services;

import com.edusyspro.api.entities.enums.Section;
import com.edusyspro.api.school.entities.Score;
import com.edusyspro.api.school.services.ScoreService;
import com.edusyspro.api.student.entities.EnrollmentEntity;
import com.edusyspro.api.student.entities.GuardianEntity;
import com.edusyspro.api.student.models.Guardian;
import com.edusyspro.api.student.models.dtos.EnrolledStudent;
import com.edusyspro.api.student.models.Enrollment;
import com.edusyspro.api.student.models.dtos.EnrolledStudentGuardian;
import com.edusyspro.api.student.repos.EnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final ScoreService scoreService;

    private final GuardianService guardianService;

    @Autowired
    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, ScoreService scoreService, GuardianService guardianService) {
        this.enrollmentRepository = enrollmentRepository;
        this.scoreService = scoreService;
        this.guardianService = guardianService;
    }

    @Override
    @Transactional
    public Enrollment enrollStudent(Enrollment enrollment) {
        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder().build();
        BeanUtils.copyProperties(enrollment, enrollmentEntity);

        GuardianEntity guardianEntity = enrollmentEntity.getStudent().getGuardian();
        if (guardianEntity != null) {
            GuardianEntity guardian = guardianService.saveOrUpdateGuardian(guardianEntity);
            enrollmentEntity.getStudent().setGuardian(guardian);
        }

        enrollmentRepository.save(enrollmentEntity);
        return enrollment;
    }

    @Override
    public Page<List<EnrolledStudent>> getEnrolledStudents(String schoolId, Pageable pageable) {
        return  enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), pageable);
    }

    @Override
    public List<EnrolledStudent> getEnrolledStudents(String schoolId, String lastname) {
        return enrollmentRepository.findEnrolledStudent(UUID.fromString(schoolId), "%" + lastname + "%");
    }

    @Override
    public Enrollment getEnrolledStudent(String schoolId, String studentId) {
        EnrolledStudent enrolledStudent = enrollmentRepository.findEnrollmentById(UUID.fromString(schoolId), UUID.fromString(studentId));
        EnrolledStudent studentEnrolled = EnrolledStudent.builder().build();
        Enrollment student = studentEnrolled.populateStudent(enrolledStudent);
        List<Score> scores = scoreService.getLastScoresByStudent(studentId);
        student.getStudent().setMarks(scores);
        return student;
    }

    @Override
    public List<Guardian> getEnrolledStudentGuardians(String schoolId, boolean isArchived) {
        List<EnrolledStudentGuardian> enrolledStudentGuardian = enrollmentRepository.findEnrolledStudentGuardian(UUID.fromString(schoolId), isArchived);
        List<Guardian> guardians = new ArrayList<>();
        if (!enrolledStudentGuardian.isEmpty()) {
            guardians = enrolledStudentGuardian.stream()
                    .map(e -> Guardian.builder()
                            .id(e.getId())
                            .firstName(e.getFirstName())
                            .lastName(e.getLastName())
                            .maidenName(e.getMaidenName())
                            .genre(e.getGenre())
                            .emailId(e.getEmailId())
                            .jobTitle(e.getJobTitle())
                            .company(e.getCompany())
                            .telephone(e.getTelephone())
                            .mobile(e.getMobile())
                            .build())
                    .toList();
        }
        return guardians;
    }
}
