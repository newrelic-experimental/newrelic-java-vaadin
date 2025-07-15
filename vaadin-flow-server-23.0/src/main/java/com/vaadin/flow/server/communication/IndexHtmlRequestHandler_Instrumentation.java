package com.vaadin.flow.server.communication;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;

@Weave(originalName = "com.vaadin.flow.server.communication.IndexHtmlRequestHandler")
public class IndexHtmlRequestHandler_Instrumentation {

	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "BootstrapHandler","IndexHtmlRequestHandler");
		return Weaver.callOriginal();
	}

}
