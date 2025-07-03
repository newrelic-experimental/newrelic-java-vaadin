package com.vaadin.flow.server.communication;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;

@Weave(originalName = "com.vaadin.flow.server.communication.JavaScriptBootstrapHandler")
public abstract class JavaScriptBootstrapHandler_Instrumentation {

	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		String[] txnNames = VaadinUtils.nameJSBootstrapTransaction(request);
		if(txnNames != null && txnNames.length > 0) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false,"BootstrapHandler",txnNames);
		}
		return Weaver.callOriginal();
	}
}
