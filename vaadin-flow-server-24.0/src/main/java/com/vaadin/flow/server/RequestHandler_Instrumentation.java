package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.RequestHandler", type = MatchType.Interface)
public abstract class RequestHandler_Instrumentation {
	
	public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Custom/Vaadin/RequestHandler/" + getClass().getSimpleName() + "/handleRequest");
		boolean result = Weaver.callOriginal();
		if(result) {
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}

}
