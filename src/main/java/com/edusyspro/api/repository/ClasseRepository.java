package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.ClassBasicValue(c.id, c.name, c.category, c.grade.section) from ClasseEntity c where c.grade.school.id = ?1
    """)
    List<ClassBasicValue> findAllBasicValue(UUID schoolID);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.grade.school.id = ?1
    """)
    Page<ClasseEssential> findAllCLassesBySchool(UUID schoolID, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.grade.school.id = ?1 and (lower(c.name) like lower(?2) or
        lower(c.category) like lower(?2) or lower(c.grade.section) like lower(?2)) order by c.createdAt desc
    """)
    List<ClasseEssential> findAllCLassesBySchool(UUID schoolID, String classeName);

    @Query("""
        select new com.edusyspro.api.dto.custom.ClasseEssential(c.id, c.name, c.category, c.grade.section, c.grade.subSection,
        c.roomNumber, c.createdAt) from ClasseEntity c where c.id = ?1
    """)
    ClasseEssential findClasseById(int id);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.personalInfo.firstName,
            c.department.boss.d_boss.personalInfo.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        ) from ClasseEntity cl join cl.principalCourse c where cl.id = ?1
    """)
    Optional<CourseEssential> findClassePrincipalCourse(int classeId);

    @Query("""
        select new com.edusyspro.api.dto.custom.GradeBasicValue(c.grade.id, c.grade.section, c.grade.subSection)
        from ClasseEntity c where c.id = ?1
    """)
    GradeBasicValue findGradeClasseId(int classeId);

    List<ClasseEntity> getClassesByGradeSection(Section section);

    boolean existsByName(String classeName);

    //TEST
    ClasseEntity getClasseById(int id);
}
