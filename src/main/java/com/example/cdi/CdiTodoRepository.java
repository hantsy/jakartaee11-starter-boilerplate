package com.example.cdi;

import com.example.domain.Todo;
import com.example.domain.Todo_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CdiTodoRepository implements CrudRepository<Todo, Long> {

    // 3.2 support @Inject EntityManager/EFM to replace @PersistenceContext
    @Inject
    EntityManager entityManager;

    @Override
    public EntityManager entityManager() {
        return this.entityManager;
    }

    public List<Todo> findByTitle(String title) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        // create query
        CriteriaQuery<Todo> query = cb.createQuery(Todo.class);
        // set the root class
        Root<Todo> root = query.from(Todo.class);

        // set predicates
        List<Predicate> predicates = new ArrayList<>();
        if (title != null && !title.isBlank()) {
            predicates.add(cb.like(root.get("title"), "%" + title + "%"));
        }
        query.where(predicates.toArray(new Predicate[0]));

        // perform query
        return this.entityManager.createQuery(query).getResultList();
    }

    public void markAsCompleted(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        // create query for updating
        CriteriaUpdate<Todo> query = cb.createCriteriaUpdate(Todo.class);
        // set the root class
        Root<Todo> root = query.from(Todo.class);

        // set predicates
        query.set(root.get("completed"), true)
                .where(cb.equal(root.get("id"), id), cb.equal(root.get("completed"), false));

        // perform query
        this.entityManager.createQuery(query).executeUpdate();
    }

    public void markAsUnCompleted(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        // create query for updating
        CriteriaUpdate<Todo> query = cb.createCriteriaUpdate(Todo.class);
        // set the root class
        Root<Todo> root = query.from(Todo.class);

        // set predicates
        // query.set(root.get("completed"), false)
        //        .where(cb.equal(root.get("id"), id), cb.equal(root.get("completed"), true));
        query.set(root.get(Todo_.completed), false)
                .where(cb.equal(root.get(Todo_.id), id), cb.equal(root.get(Todo_.completed), true));

        // perform query
        this.entityManager.createQuery(query).executeUpdate();
    }
}
