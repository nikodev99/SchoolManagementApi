package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.custom.GuardianEssential;
import com.edusyspro.api.repository.GuardianRepository;
import com.edusyspro.api.service.interfaces.GuardianService;
import com.edusyspro.api.service.interfaces.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GuardianServiceImp implements GuardianService {

    private final EntityManager entityManager;

    private final GuardianRepository guardianRepository;
    private final StudentService studentService;

    @Autowired
    public GuardianServiceImp(
            EntityManager entityManager,
            GuardianRepository guardianRepository,
            StudentService studentService
    ) {
        this.entityManager = entityManager;
        this.guardianRepository = guardianRepository;
        this.studentService = studentService;
    }

    @Transactional
    public GuardianEntity saveOrUpdateGuardian(GuardianEntity guardianEntity) {
        if (guardianEntity.getId() == null) {
            return guardianRepository.save(guardianEntity);
        }else {
            return entityManager.merge(guardianEntity);
        }
    }

    @Override
    public GuardianDTO findGuardianById(String id) {
        GuardianEssential essential = guardianRepository.findGuardianEntityById(UUID.fromString(id));
        GuardianDTO guardianDTO = new GuardianDTO();
        if (essential != null)
            guardianDTO = GuardianEssential.populateGuardian(essential);

        return guardianDTO;
    }

    @Override
    public GuardianDTO findGuardianByIdWithStudents(String guardianId) {
        GuardianDTO guardianDTO = findGuardianById(guardianId);
        List<StudentDTO> studentDTO = studentService.findStudentByGuardian(guardianId);
        guardianDTO.setStudentDTOS(studentDTO);
        return guardianDTO;
    }

    @Override
    public List<GuardianDTO> findAll() {
        List<GuardianEssential> allGuardians = guardianRepository.findAllGuardians();
        List<GuardianDTO> guardianDTOS = new ArrayList<>();
        if (!allGuardians.isEmpty())
            guardianDTOS = allGuardians.stream()
                    .map(GuardianEssential::populateGuardian)
                    .toList();

        return guardianDTOS;
    }
}
