package com.vaadin.flow.server.communication;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;

@Weave(originalName = "com.vaadin.flow.server.communication.SessionRequestHandler")
public abstract class SessionRequestHandler_Instrumentation {

	@Trace
	public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		boolean handled = Weaver.callOriginal();
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Handled", handled);
		return handled;
	}

}
