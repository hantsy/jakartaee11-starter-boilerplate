package com.example.web;

import com.example.domain.Status;
import com.example.domain.Todo;
import com.example.ejb.EjbTodoRepository;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@Named("todoList")
public class TodoList implements Serializable {
    private static final Logger LOGGER= Logger.getLogger(TodoList.class.getName());
    
    @Inject
    FacesContext facesContext;

    @Inject
    EjbTodoRepository todoRepository;

    List<Todo> todos;

    TodoForm form;

    private String filter = "ALL";
    private long allCount;
    private long pendingCount;
    private long completedCount;

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public TodoForm getForm() {
        return form;
    }

    public void setForm(TodoForm form) {
        this.form = form;
    }
    
    public void init(){
        LOGGER.info("init...");
        loadTodos();
        this.form = new TodoForm();
    }

    public void loadTodos() {
        if ("PENDING".equals(filter)) {
            this.todos = todoRepository.findByStatus(Status.PENDING);
        } else if ("COMPLETED".equals(filter)) {
            this.todos = todoRepository.findByStatus(Status.COMPLETED);
        } else {
            this.todos = todoRepository.findAll();
        }
        this.allCount = todoRepository.count();
        this.pendingCount = todoRepository.countByStatus(Status.PENDING);
        this.completedCount = todoRepository.countByStatus(Status.COMPLETED);
    }

    public void changeFilter(String newFilter) {
        LOGGER.log(Level.INFO, "changing filter to: {0}", newFilter);
        this.filter = newFilter;
        loadTodos();
    }

    public void cancelEdit() {
        this.form = new TodoForm();
    }

    public void saveTodo() {
        LOGGER.log(Level.INFO, "saving form:{0}", new Object[]{this.form});
        if (this.form.getId() == null) {
            todoRepository.save(Todo.of(this.form.getTitle()));
        } else {
            var existed = todoRepository.findById(this.form.getId());
            existed.setTitle(this.form.getTitle());
            todoRepository.save(existed);
        }
        loadTodos();
        this.form = new TodoForm();
        facesContext.addMessage(null,new FacesMessage("Todo was saved successfully."));
    }

    public void editTodo(Long id) {
        LOGGER.log(Level.INFO, "editting todo:{0}", new Object[]{id});
        var todo = todoRepository.findById(id);
        this.form = new TodoForm(todo.getId(), todo.getTitle());
    }

    public void deleteTodo(Long id) {
        LOGGER.log(Level.INFO, "deleting todo:{0}", new Object[]{id});
        todoRepository.deleteById(id);
        loadTodos();
        facesContext.addMessage(null,new FacesMessage("Todo '%s' was deleted successfully.".formatted(id.toString())));
    }
    
    public void toggleCompletedStatus(Long id) {
         LOGGER.log(Level.INFO, "toggle status:{0}", new Object[]{id});
        var todo = todoRepository.findById(id);
        if(todo.getStatus() == Status.COMPLETED) {
            markAsUnCompleted(todo);
        }else{
            markAsCompleted(todo);
        }
    }

    public void markAsCompleted(Todo todo) {
        todoRepository.markAsCompleted(todo.getId());
        loadTodos();
    }

    public void markAsUnCompleted(Todo todo) {
        todoRepository.markAsPending(todo.getId());
        loadTodos();
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public long getAllCount() {
        return allCount;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public long getCompletedCount() {
        return completedCount;
    }

}
