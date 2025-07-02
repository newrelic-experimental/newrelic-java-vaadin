package com.vaadin.flow.server.communication.rpc;

import java.util.Optional;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.internal.StateNode;

import elemental.json.JsonObject;

@Weave(type = MatchType.BaseClass, originalName = "com.vaadin.flow.server.communication.rpc.AbstractRpcInvocationHandler")
public abstract class AbstractRpcInvocationHandler_Instrumentation {

	public abstract String getRpcType();
	
	@Trace
	public Optional<Runnable> handle(UI ui, JsonObject invocationJson) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, true, "RPCRequest", "Vaadin","RPCRequest",getRpcType());
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Vaadin","RpcInvocationHandler",getClass().getSimpleName(),"handle");
		traced.addCustomAttribute("RPCType", getRpcType());
		return Weaver.callOriginal();
	}

	@Trace
	protected Optional<Runnable> handleNode(StateNode node, JsonObject invocationJson) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","AbstractRpcInvocationHandler",getClass().getSimpleName(),"handleNode");
		return Weaver.callOriginal();
	}
}
