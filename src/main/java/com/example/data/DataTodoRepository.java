package com.example.data;

import com.example.domain.Todo;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface DataTodoRepository extends CrudRepository<Todo, Long> {
}