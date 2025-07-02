package com.vaadin.flow.server;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;

@Weave(originalName = "com.vaadin.flow.server.VaadinService", type = MatchType.BaseClass)
public abstract class VaadinService_Instrumentation {

	public abstract String getServiceName();
	public abstract String getContextRootRelativePath(VaadinRequest request);
	public abstract VaadinContext getContext();
	protected abstract String getSessionAttributeName();
	
	@Trace
	public void handleRequest(VaadinRequest request, VaadinResponse response) {
		String serviceName = getServiceName();
		if(serviceName == null) {
			serviceName = "UnknownService";
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "VaadinService", "Vaadin","Service",serviceName);
		traced.setMetricName("Custom","Vaadin","VaadinService",getClass().getSimpleName(),serviceName,"handleRequest");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		VaadinUtils.addVaadinRequest(attributes, request);
		VaadinUtils.addAttribute(attributes, "ServiceName", serviceName);
		VaadinUtils.addAttribute(attributes, "ContextRootRelativePath", getContextRootRelativePath(request));
		VaadinUtils.addAttribute(attributes, "SessionAttributeName", getSessionAttributeName());
		VaadinUtils.addVaadinContext(attributes, getContext());
		
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
