package com.edusyspro.api.repository;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.model.HealthCondition;
import com.edusyspro.api.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    StudentEntity findStudentEntityById(UUID id);

    @Query("select s.address from StudentEntity s where s.id = ?1")
    Optional<Address> findStudentEntityAddressByStudentId(UUID id);

    @Query("select s.guardian from StudentEntity s where s.id = ?1")
    Optional<GuardianEntity> getStudentEntityGuardianByStudentId(UUID id);

    @Query("select s.healthCondition from StudentEntity s where s.id = ?1")
    Optional<HealthCondition> findStudentEntityHealthConditionByStudentId(UUID id);
}
