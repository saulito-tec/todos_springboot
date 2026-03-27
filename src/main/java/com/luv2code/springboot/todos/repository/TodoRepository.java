package com.luv2code.springboot.todos.repository;

import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

  List<Todo> findByOwner(User owner);

  Optional<Todo> findByIdAndOwner(Long id, User owner);
}
