package com.vaadin.cdi;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.server.VaadinServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@Weave
public abstract class CdiVaadinServlet extends VaadinServlet {

	public static String getCurrentServletName() {
		return Weaver.callOriginal();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		Weaver.callOriginal();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "VaadinServlet", "VaadinServlet",getServletName());
	}
}
