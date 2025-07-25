package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.Command", type = MatchType.Interface)
public abstract class Command_Instrumentation {

	@Trace
	public void execute() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","Command",getClass().getName(),"execute");
		Weaver.callOriginal();
	}
}
