package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AssignmentDTO;
import com.edusyspro.api.dto.custom.AssignmentEssential;
import com.edusyspro.api.repository.AssignmentRepository;
import com.edusyspro.api.service.interfaces.AssignmentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public List<AssignmentDTO> findSomeAssignmentsPreparedByTeacher(long teacherId) {
        return assignmentRepository.findAssignmentsByTeacher(teacherId, PageRequest.of(0, 5))
                .map(AssignmentEssential::toDTO)
                .toList();
    }
}
