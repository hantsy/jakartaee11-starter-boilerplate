package com.example.data;

import com.example.domain.Status;
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

    // @Query("update Todo set status=com.example.domain.Status.COMPLETED where id=:id")
    // void markAsCompleted(@Param("id") Long id);
    default void markAsCompleted(Long id) {
        updateStatus(id, Status.COMPLETED);
    }

    // @Query("update Todo set status=com.example.domain.Status.PENDING where id=:id")
    // void markAsUnCompleted(@Param("id") Long id)
    default void markAsUnCompleted(Long id) {
        updateStatus(id, Status.PENDING);
    }

    @Query("update Todo set status=:status where id=:id")
    void updateStatus(@Param("id") Long id, @Param("status") Status status);

    @Delete()
    void deleteAll();
}