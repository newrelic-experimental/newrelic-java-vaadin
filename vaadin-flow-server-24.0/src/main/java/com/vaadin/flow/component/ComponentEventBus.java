package com.vaadin.flow.component;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;

@Weave
public abstract class ComponentEventBus {

	@Trace
	public void fireEvent(ComponentEvent event) {
		Component component = event.getSource();
		VaadinUtils.dumpComponent(component);
		HashMap<String, Object> attributes = new HashMap<String, Object>();

		Class<?> componentClass = component.getClass();
		VaadinUtils.addAttribute(attributes,"componentClass",componentClass.getName());
		String routePath = VaadinUtils.getRoutePath(component);
		if(routePath != null) {
			VaadinUtils.addAttribute(attributes,"RoutePath",VaadinUtils.getRoutePath(component));
			if(routePath != VaadinUtils.NO_ROUTE_PATH && routePath != VaadinUtils.NULL_STRING) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "VaadinRoute", "Vaadin","Route", routePath);
			}
		}
		
		VaadinUtils.addAttribute(attributes, "Event-Class", event.getClass().getName());
		VaadinUtils.addAttribute(attributes, "Event-Source", event.getSource());
		VaadinUtils.addAttribute(attributes, "Event-IsFromClient", event.isFromClient());
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
