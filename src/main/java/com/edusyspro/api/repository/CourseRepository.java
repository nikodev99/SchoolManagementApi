package com.edusyspro.api.repository;

import com.edusyspro.api.dto.custom.CourseBasicValue;
import com.edusyspro.api.dto.custom.CourseEssential;
import com.edusyspro.api.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseBasicValue(c.id, c.course, c.abbr) from Course c where c.department.school.id = ?1
    """)
    List<CourseBasicValue> findAllCourses(UUID schoolId);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.personalInfo.firstName,
            c.department.boss.d_boss.personalInfo.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        ) from Course c where c.department.school.id = ?1 and c.department.boss.current = true
    """)
    Page<CourseEssential> findAllCoursesBySchoolId(UUID schoolId, Pageable pageable);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.personalInfo.firstName,
            c.department.boss.d_boss.personalInfo.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        ) from Course c where c.department.school.id = ?1 and c.department.boss.current = true and (lower(c.course) like lower(?2)
        or lower(c.abbr) like lower(?2) or lower(c.department.code) like lower(?2))
    """)
    List<CourseEssential> findAllCoursesBySchoolId(UUID schoolId, String courseName);

    @Query("""
        select new com.edusyspro.api.dto.custom.CourseEssential(
            c.id, c.course, c.abbr, c.department.id, c.department.name, c.department.code, c.department.purpose,
            c.department.boss.d_boss.id, c.department.boss.current, c.department.boss.d_boss.personalInfo.firstName,
            c.department.boss.d_boss.personalInfo.lastName, c.department.boss.startPeriod, c.department.boss.endPeriod, c.createdAt
        )from Course c where c.id = ?1 and c.department.boss.current = true
    """)
    CourseEssential findCourseById(Integer id);

    boolean existsCourseByCourseAndAbbr(String course, String abbr);

    //TEST
    Course getCourseByAbbrContainingIgnoreCase(String abbr);

}
