package com.example.ejb;

import com.example.domain.Status;
import com.example.domain.Todo;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute
// CDI compatible @Transactional annotation does not work with EJB in OpenLiberty,
// see: https://github.com/OpenLiberty/open-liberty/issues/25363
public class EjbTodoRepository extends EntityRepository<Todo, Long> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
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
        query.set(root.get("status"), Status.COMPLETED)
                .where(cb.equal(root.get("id"), id), cb.equal(root.get("status"), Status.PENDING));

        // perform query
        this.entityManager.createQuery(query).executeUpdate();
    }

    public void markAsPending(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        // create query for updating
        CriteriaUpdate<Todo> query = cb.createCriteriaUpdate(Todo.class);
        // set the root class
        Root<Todo> root = query.from(Todo.class);

        // set predicates
        query.set(root.get("status"), Status.PENDING)
                .where(cb.equal(root.get("id"), id), cb.equal(root.get("status"), Status.COMPLETED));

        // perform query
        this.entityManager.createQuery(query).executeUpdate();
    }

    public List<Todo> findByStatus(Status status) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> query = cb.createQuery(Todo.class);
        Root<Todo> root = query.from(Todo.class);
        query.where(cb.equal(root.get("status"), status));
        return this.entityManager.createQuery(query).getResultList();
    }

    public long countByStatus(Status status) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Todo> root = query.from(Todo.class);
        query.select(cb.count(root));
        query.where(cb.equal(root.get("status"), status));
        return this.entityManager.createQuery(query).getSingleResult();
    }
}
