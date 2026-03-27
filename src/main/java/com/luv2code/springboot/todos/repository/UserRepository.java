package com.luv2code.springboot.todos.repository;

import com.luv2code.springboot.todos.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("SELECT COUNT(u) FROM USER u JOIN u.authorities a WHERE a.authority = 'ROLE_ADMIN' ")
  long countAdminUsers();
}
