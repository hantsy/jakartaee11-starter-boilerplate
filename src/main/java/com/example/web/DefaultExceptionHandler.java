package com.example.web;

import com.example.domain.TodoNotFoundException;
import jakarta.faces.FacesException;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private final static Logger LOG = Logger.getLogger(DefaultExceptionHandler.class.getName());

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handle() throws FacesException {
        LOG.log(Level.INFO, "starting custom exception handling...");
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = event.getContext();
            Throwable t = context.getException();
            LOG.log(Level.INFO, "Caught exception@{0}", t.getClass().getName());
            //t.printStackTrace();
            switch (t) {
                case ViewExpiredException viewExpiredException -> {
                    try {
                        handleViewExpiredException(viewExpiredException);
                    } finally {
                        events.remove();
                    }
                }
                case TodoNotFoundException todoNotFoundException -> {
                    try {
                        handleNotFoundException(todoNotFoundException);
                    } finally {
                        events.remove();
                    }
                }
                default -> {
                }
            }

            LOG.log(Level.INFO, "exception handling is done...");
        }
        getWrapped().handle();

    }

    private void handleViewExpiredException(ViewExpiredException vee) {
        LOG.log(Level.INFO, " handling viewExpiredException{0}", vee.getMessage());
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = vee.getViewId();
        LOG.log(Level.INFO, "view id @{0}", viewId);
        // fallback to application root when viewId cannot be determined,
        // otherwise navigating to null outcome redisplay the expired view and loops
        String outcome = (viewId != null) ? viewId : "/todos.xhtml?faces-redirect=true";
        NavigationHandler nav = context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, outcome);
        context.renderResponse();
    }

    private void handleNotFoundException(TodoNotFoundException e) {
        LOG.log(Level.INFO, "handling TodoNotFoundException:{0}", e.getMessage());
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = "/error.xhtml";
        LOG.log(Level.INFO, "view id @{0}", viewId);

        ViewHandler viewHandler = context.getApplication().getViewHandler();
        context.setViewRoot(viewHandler.createView(context, viewId));
        context.getViewRoot().getViewMap(true).put("ex", e);
        context.getPartialViewContext().setRenderAll(true);
        context.renderResponse();
    }
}
