package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;

@Weave(originalName = "com.vaadin.flow.server.BootstrapHandler")
public abstract class BootstrapHandler_Instrumentation {

	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "BootstrapHandler",VaadinUtils.nameBootstrapTransaction(request));
		return Weaver.callOriginal();
	}
}
