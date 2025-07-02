package com.vaadin.flow.server;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Weave(originalName = "com.vaadin.flow.server.StaticFileHandler", type = MatchType.Interface)
public abstract class StaticFileHandler_Instrumentation {

	@Trace
	public boolean serveStaticResource(HttpServletRequest request, HttpServletResponse response) {
		return Weaver.callOriginal();
	}
}
