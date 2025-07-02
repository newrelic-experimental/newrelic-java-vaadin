package com.vaadin.flow.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.StaticFileHandler", type = MatchType.Interface)
public abstract class StaticFileHandler_Instrumentation {

	@Trace
	public boolean serveStaticResource(HttpServletRequest request, HttpServletResponse response) {
		return Weaver.callOriginal();
	}
}
