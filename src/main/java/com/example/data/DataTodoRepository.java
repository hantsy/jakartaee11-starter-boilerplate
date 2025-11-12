package com.example.data;

import com.example.domain.Todo;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface DataTodoRepository extends CrudRepository<Todo, Long> {
}