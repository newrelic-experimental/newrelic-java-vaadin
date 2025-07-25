package com.newrelic.instrumentation.labs.vaadin.flowserver;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class RunnableWrapper implements Runnable {
	
	private static boolean isTransformed = false;
	
	private Runnable delegate = null;
	private Token token = null;
	
	public RunnableWrapper(Runnable r) {
		delegate = r;
		token = NewRelic.getAgent().getTransaction().getToken();
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async = true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		if(delegate != null) {
			delegate.run();
		}
	}

}
