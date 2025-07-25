package com.vaadin.flow.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "com.vaadin.flow.server.StaticFileServer")
public abstract class StaticFileServer_Instrumentation {

	@Trace
	public boolean serveStaticResource(HttpServletRequest request, HttpServletResponse response) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "StaticFileServer", "Vaadin","StaticFileServer");
		return Weaver.callOriginal();
	}
	
	protected void writeCacheHeaders(String filenameWithPath, HttpServletResponse response) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("FilePath",filenameWithPath);
		
		Weaver.callOriginal();
	}
}
