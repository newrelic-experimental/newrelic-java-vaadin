package com.vaadin.flow.internal.nodefeature;

import java.util.HashMap;
import java.util.Optional;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.Element;

@Weave
public abstract class ElementListenerMap {

	@Trace
	public void fireEvent(DomEvent event) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		VaadinUtils.addDomEvent(attributes, event);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Element element = event.getSource();
		Optional<Component> componentOption = element.getComponent();
		if(componentOption.isPresent()) {
			VaadinUtils.dumpComponentWithSource(componentOption.get(), "In ElementListenerMap.fireEvent");
		}
		Weaver.callOriginal();
	}
}
