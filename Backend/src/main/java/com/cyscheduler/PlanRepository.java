package com.cyscheduler;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {
    Set<Plan> findAll();
    Plan findByUpid(@Param("upid") Integer upid);
    Plan findByName(@Param("name") String name);
    Set<Plan> findByUuid(@Param("uuid") Integer uuid);
}
