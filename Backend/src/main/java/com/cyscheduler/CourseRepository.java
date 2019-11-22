package com.cyscheduler;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
    //Collection<Course> findByUcid(@Param("ucid") Integer ucid);
    Set<Course> findAll();
    Set<Course> findByCourseInput(@Param("courseInput") String courseInput);
    Set<Course> findByDept(@Param("dept") String dept);
}
