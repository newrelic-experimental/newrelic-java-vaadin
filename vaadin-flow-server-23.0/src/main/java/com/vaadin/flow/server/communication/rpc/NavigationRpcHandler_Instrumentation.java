package com.vaadin.flow.server.communication.rpc;

import java.util.Optional;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.RunnableWrapper;
import com.vaadin.flow.component.UI;

import elemental.json.JsonObject;

@Weave(originalName = "com.vaadin.flow.server.communication.rpc.NavigationRpcHandler")
public abstract class NavigationRpcHandler_Instrumentation {

	public abstract String getRpcType();
	
	@Trace
	public Optional<Runnable> handle(UI ui, JsonObject invocationJson) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Vaadin","RpcInvocationHandler",getClass().getSimpleName(),"handle");
		traced.addCustomAttribute("RPCType", getRpcType());
		Optional<Runnable> optRunnable = Weaver.callOriginal();
		
		return optRunnable.flatMap(r -> {
			RunnableWrapper wrapper = new RunnableWrapper(r);
			return Optional.of(wrapper);
		});
	}

}
