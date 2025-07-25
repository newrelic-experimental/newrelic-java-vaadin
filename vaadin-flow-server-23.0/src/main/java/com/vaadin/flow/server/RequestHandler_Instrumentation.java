package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.RequestHandler", type = MatchType.Interface)
public abstract class RequestHandler_Instrumentation {
	
	public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		long startTime = System.currentTimeMillis();
		
		boolean result = Weaver.callOriginal();
		if(result) {
			long endTime = System.currentTimeMillis();
			String metricName = "Custom/Vaadin/RequestHandler/" + getClass().getSimpleName() + "/handleRequest";
			NewRelic.recordResponseTimeMetric(metricName, endTime-startTime);
		}
		return result;
	}

}
