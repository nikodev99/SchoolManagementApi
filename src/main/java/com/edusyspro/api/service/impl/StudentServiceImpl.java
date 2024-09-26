package com.edusyspro.api.service.impl;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.repository.ScheduleRepository;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import com.edusyspro.api.dto.Student;
import com.edusyspro.api.repository.StudentRepository;
import com.edusyspro.api.repository.context.StudentUpdateContext;
import com.edusyspro.api.service.interfaces.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final StudentUpdateContext studentUpdateContext;

    @Autowired
    public StudentServiceImpl(
            StudentRepository studentRepository,
            ScheduleRepository scheduleRepository,
            StudentUpdateContext studentUpdateContext) {
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
        this.studentUpdateContext = studentUpdateContext;
    }

    @Override
    public Student findStudentById(String id) {
        Student student = Student.builder().build();
        StudentEntity studentEntity = studentRepository.findStudentEntityById(UUID.fromString(id));
        BeanUtils.copyProperties(studentEntity, student);
        return student;
    }

    @Override
    public Address getStudentAddress(String studentId) {
        return studentRepository.findStudentEntityAddressByStudentId(UUID.fromString(studentId))
                .orElseThrow();
    }

    @Override
    public GuardianEntity getStudentGuardian(String studentId) {
        return studentRepository.getStudentEntityGuardianByStudentId(UUID.fromString(studentId))
                .orElseThrow();
    }

    @Override
    public HealthCondition getStudentHealthCondition(String studentId) {
        return studentRepository.findStudentEntityHealthConditionByStudentId(UUID.fromString(studentId))
                .orElse(null);
    }

    @Override
    public int updateStudent(String field, Object value, String studentId) {
        return studentUpdateContext.updateStudentByField(field, value, UUID.fromString(studentId));
    }

    @Override
    public int updateStudentAddress(String field, Object value, long addressId) {
        return studentUpdateContext.updateAddressByField(field, value, addressId);
    }

    @Override
    public int updateStudentHealth(String field, Object value, String studentId) {
        return studentUpdateContext.updateHealthByField(field, value, UUID.fromString(studentId));
    }
}
