package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.VaadinService", type = MatchType.BaseClass)
public abstract class VaadinService_Instrumentation {

	public abstract String getServiceName();
	
	@Trace
	public void handleRequest(VaadinRequest request, VaadinResponse response) {
		String serviceName = getServiceName();
		if(serviceName == null) {
			serviceName = "UnknownService";
		}
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "VaadinService", "Vaadin","Service",serviceName);
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","VaadinService",getClass().getSimpleName(),serviceName,"handleRequest");
		Weaver.callOriginal();
	}
}
