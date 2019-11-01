package com.cyscheduler;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Collection<User> findByUuid(@Param("uuid") Integer uuid);
}
