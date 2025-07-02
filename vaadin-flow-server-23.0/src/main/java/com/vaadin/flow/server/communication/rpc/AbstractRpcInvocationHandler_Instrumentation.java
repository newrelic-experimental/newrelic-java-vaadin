package com.vaadin.flow.server.communication.rpc;

import java.util.Optional;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.internal.StateNode;

import elemental.json.JsonObject;

@Weave(originalName = "com.vaadin.flow.server.communication.rpc.AbstractRpcInvocationHandler", type = MatchType.BaseClass)
public abstract class AbstractRpcInvocationHandler_Instrumentation {

	@Trace
	protected Optional<Runnable> handleNode(StateNode node, JsonObject invocationJson) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","AbstractRpcInvocationHandler",getClass().getSimpleName(),"handleNode");
		return Weaver.callOriginal();
	}
}
