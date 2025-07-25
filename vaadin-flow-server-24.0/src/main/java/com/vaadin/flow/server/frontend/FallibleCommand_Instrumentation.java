package com.vaadin.flow.server.frontend;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.frontend.FallibleCommand", type = MatchType.Interface)
public abstract class FallibleCommand_Instrumentation {

	@Trace
	public void execute() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","FallibleCommand",getClass().getSimpleName(),"execute");
		Weaver.callOriginal();
	}
}
