package com.example.cdi;

import com.example.domain.Todo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Transactional
public class CdiDataInitializer {
    private static Logger LOG = Logger.getLogger(CdiDataInitializer.class.getName());

    @Inject
    CdiTodoRepository todoRepository;

    public void init(@Observes Startup event) {
        LOG.log(Level.SEVERE, "initializing sample data: {0}", event);
        todoRepository.deleteAll();
        todoRepository.saveAll(
                List.of(
                        Todo.of("Say Hello to Jakarta EE 11(CdiDataInitializer)"),
                        Todo.of("Upgrade to Jakarta EE 11(CdiDataInitializer)")
                )
        );
    }
}
