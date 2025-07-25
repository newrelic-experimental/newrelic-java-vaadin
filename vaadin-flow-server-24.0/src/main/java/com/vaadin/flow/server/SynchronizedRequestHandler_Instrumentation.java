package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.SynchronizedRequestHandler", type = MatchType.BaseClass)
public abstract class SynchronizedRequestHandler_Instrumentation {

	@Trace
	public boolean synchronizedHandleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","SynchronizedRequestHandler",getClass().getSimpleName(),"synchronizedHandleRequest");
		return Weaver.callOriginal();
	}
}
