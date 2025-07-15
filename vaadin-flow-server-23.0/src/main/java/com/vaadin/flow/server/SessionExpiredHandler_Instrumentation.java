package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.SessionExpiredHandler", type = MatchType.Interface)
public abstract class SessionExpiredHandler_Instrumentation {

	@Trace
	public boolean handleSessionExpired(VaadinRequest request, VaadinResponse response) {
		boolean handled = Weaver.callOriginal();
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Handled", handled);

		return handled;
	}
}
