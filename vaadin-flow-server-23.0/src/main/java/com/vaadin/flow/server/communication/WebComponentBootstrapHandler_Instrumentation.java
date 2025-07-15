package com.vaadin.flow.server.communication;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;

@Weave(originalName = "com.vaadin.flow.server.communication.WebComponentBootstrapHandler")
public abstract class WebComponentBootstrapHandler_Instrumentation {
	
	protected abstract String getRequestUrl(VaadinRequest request);

	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		String requestURL = getRequestUrl(request);
		if(requestURL != null && !requestURL.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "WebComponentBootstrapHandler",requestURL);
		}
		return Weaver.callOriginal();
	}
}
