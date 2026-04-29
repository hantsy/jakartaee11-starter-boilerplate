package com.example.data;

import com.example.domain.Todo;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Delete;
import jakarta.data.repository.Param;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DataTodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByTitleLike(String title);

    @Query("update Todo set status='COMPLETED' where id=:id")
    void markAsCompleted(@Param("id") Long id);

    @Query("update Todo set status='PENDING' where id=:id")
    void markAsUnCompleted(@Param("id") Long id);

    @Delete()
    void deleteAll();
}