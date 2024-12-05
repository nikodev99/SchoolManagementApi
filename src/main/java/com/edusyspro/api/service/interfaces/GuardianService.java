package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.model.GuardianEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuardianService {

    GuardianEntity saveOrUpdateGuardian(GuardianEntity guardian);

    GuardianDTO findGuardianById(String id);

    GuardianDTO findGuardianByIdWithStudents(String guardianId);

    List<GuardianDTO> findAll();

}
