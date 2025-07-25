package com.vaadin.flow.router;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.vaadin.flowserver.VaadinUtils;
import com.vaadin.flow.component.UI;

import elemental.json.JsonValue;

@Weave(originalName = "com.vaadin.flow.router.Router")
public abstract class Router_Instrumentation {

	@Trace
	public int navigate(UI ui, Location location, NavigationTrigger trigger, JsonValue state) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		Map<String, Object> attributes = new HashMap<String, Object>();
		VaadinUtils.addAttribute(attributes, "TriggerType", trigger.name());
		VaadinUtils.addLocation(attributes, location);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
