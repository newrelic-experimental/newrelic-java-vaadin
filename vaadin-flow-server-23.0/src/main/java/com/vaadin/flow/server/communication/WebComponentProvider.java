package com.vaadin.flow.server.communication;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;

@Weave
public abstract class WebComponentProvider {

	protected String generateNPMResponse(String tagName, VaadinRequest request, VaadinResponse response) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "VaadinWebcomponent", "Vaadin","WebComponentProvider",tagName);
		return Weaver.callOriginal();
	}
}
