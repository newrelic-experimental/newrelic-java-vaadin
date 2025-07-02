package com.vaadin.flow.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.ErrorHandlingCommand", type = MatchType.Interface)
public abstract class ErrorHandlingCommand_Instrumentation {

	public void handleError(Exception exception) {
		NewRelic.noticeError(exception);
		Weaver.callOriginal();
	}
}
