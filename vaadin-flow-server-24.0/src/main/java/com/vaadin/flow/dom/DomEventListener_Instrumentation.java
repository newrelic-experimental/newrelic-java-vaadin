package com.vaadin.flow.dom;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface, originalName = "com.vaadin.flow.dom.DomEventListener")
public abstract class DomEventListener_Instrumentation {

	@Trace
	public void handleEvent(DomEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","DomEventListener",getClass().getName(),"handleEvent");
		Weaver.callOriginal();
	}
}
