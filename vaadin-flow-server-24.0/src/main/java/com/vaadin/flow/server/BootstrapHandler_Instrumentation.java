package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.BootstrapHandler", type = MatchType.BaseClass)
public abstract class BootstrapHandler_Instrumentation {

	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "BootstrapHandler", "Vaadin","BootstrapHandler",getClass().getSimpleName());
		return Weaver.callOriginal();
	}
}
