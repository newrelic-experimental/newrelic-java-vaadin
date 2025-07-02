package com.vaadin.flow.dom;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.vaadin.flow.dom.DomEvent;

@Weave(type = MatchType.Interface)
public abstract class DomEventListener {

	public void handleEvent(DomEvent event) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Vaadin","DomEventListener",getClass().getName(),"handleEvent");
		Weaver.callOriginal();
	}
}
