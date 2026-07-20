package com.example.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AfterPhase;
import jakarta.faces.event.BeforePhase;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Enables messages to be rendered on different pages from which they were set.
 * <p>
 * This is performed by moving the FacesMessage objects:
 *
 * <li>After each phase where messages may be added, this moves the messages from
 * the page-scoped FacesContext to the session-scoped session map.
 * </li>
 * <li>Before messages are rendered, this moves the messages from the session-scoped
 * session map back to the page-scoped FacesContext.
 * </li>
 * Only messages that are not associated with a particular component are ever
 * moved. These are the only messages that can be rendered on a page that is different
 * from where they originated.
 *
 * @author <a href="mailto:jesse@odel.on.ca">Jesse Wilson</a>
 * @author <a href="mailto:mkuipers@sourceallies.com">Max Kuipers</a>
 */
@ApplicationScoped
public class MessageHandler {

    /**
     * a name to save messages in the session under
     */
    private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";

    /**
     * Handle a notification that the RENDER_RESPONSE phase is about to begin.
     */
    public void beforeRenderResponse(@Observes @BeforePhase(PhaseId.RENDER_RESPONSE) PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        restoreMessages(facesContext);
    }

    /**
     * Handle a notification that the processing for a particular phase has just
     * been completed.
     */
    public void afterPhase(@Observes @AfterPhase(PhaseId.ANY_PHASE) PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();

        if (phaseId == PhaseId.APPLY_REQUEST_VALUES ||
                phaseId == PhaseId.PROCESS_VALIDATIONS ||
                phaseId == PhaseId.UPDATE_MODEL_VALUES ||
                phaseId == PhaseId.INVOKE_APPLICATION) {

            FacesContext facesContext = event.getFacesContext();
            saveMessages(facesContext);
        }
    }

    /**
     * Remove the messages that are not associated with any particular component
     * from the faces context and store them to the user's session.
     *
     * @return the number of removed messages.
     */
    private int saveMessages(FacesContext facesContext) {
        // remove messages from the context
        List<FacesMessage> messages = new ArrayList<>();
        for (Iterator<FacesMessage> i = facesContext.getMessages(null); i.hasNext(); ) {
            messages.add(i.next());
            i.remove();
        }
        // store them in the session
        if (messages.isEmpty()) {
            return 0;
        }
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        // if there already are messages
        @SuppressWarnings("unchecked")
        List<FacesMessage> existingMessages = (List<FacesMessage>) sessionMap.get(sessionToken);
        if (existingMessages != null) {
            existingMessages.addAll(messages);
        } else {
            sessionMap.put(sessionToken, messages); // if these are the first messages
        }

        return messages.size();
    }

    /**
     * Remove the messages that are not associated with any particular component
     * from the user's session and add them to the faces context.
     *
     * @return the number of restored messages.
     */
    private int restoreMessages(FacesContext facesContext) {
        // remove messages from the session
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        @SuppressWarnings("unchecked")
        List<FacesMessage> messages = (List<FacesMessage>) sessionMap.remove(sessionToken);
        // store them in the context
        if (messages == null) {
            return 0;
        }
        int restoredCount = messages.size();
        for (Iterator<FacesMessage> i = messages.iterator(); i.hasNext(); ) {
            facesContext.addMessage(null, i.next());
        }

        return restoredCount;
    }
}
