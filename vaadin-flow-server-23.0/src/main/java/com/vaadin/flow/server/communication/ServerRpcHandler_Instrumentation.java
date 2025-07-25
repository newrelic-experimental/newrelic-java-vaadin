package com.vaadin.flow.server.communication;

import java.io.Reader;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;

@Weave(originalName = "com.vaadin.flow.server.communication.ServerRpcHandler")
public abstract class ServerRpcHandler_Instrumentation {

	@Trace
	public void handleRpc(UI ui, Reader reader, VaadinRequest request) {
		
		Weaver.callOriginal();
	}
}
