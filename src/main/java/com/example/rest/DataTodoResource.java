package com.example.rest;


import com.example.data.DataTodoRepository;
import com.example.domain.Todo;
import com.example.domain.TodoNotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@RequestScoped
@Path("datatodos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataTodoResource {

    @Inject
    DataTodoRepository todoRepository;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getAll(@QueryParam("title") String title) {
        var todos = title == null || title.isBlank()
                ? todoRepository.findAll().toList()
                : todoRepository.findByTitleLike("%" + title.trim() + "%");
        return Response.ok(todos).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        return todoRepository.findById(id)
                .map(todo -> Response.ok(todo).build())
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @POST
    public Response saveTodo(@Valid CreateTodoCommand data) {
        var saved = todoRepository.save(Todo.of(data.title()));
        return Response.created(URI.create(uriInfo.getBaseUri() + "/todos/" + saved.getId())).build();
    }

    @PUT
    @Path("{id}")
    public Response updateById(@PathParam("id") Long id, @Valid UpdateTodoCommand data) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(data.title());
                    return Response.noContent().build();
                })
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") Long id) {
        todoRepository.deleteById(id);
        return Response.noContent().build();
    }

    @POST
    @Path("{id}/completed")
    public Response markAsCompleted(@PathParam("id") Long id) {
        todoRepository.markAsCompleted(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}/completed")
    public Response markAsUnCompleted(@PathParam("id") Long id) {
        todoRepository.markAsUnCompleted(id);
        return Response.noContent().build();
    }

}
