package com.newrelic.instrumentation.labs.vaadin.flowserver;

import java.util.Map;
import java.util.Optional;

import com.vaadin.flow.router.Location;

public class VaadinUtils {

	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(value != null && attributes != null && key != null && !key.isEmpty()) {
			attributes.put(key, value);
		}
	}
	
	public static void addLocation(Map<String, Object> attributes, Location location) {
		if(location != null) {
			addAttribute(attributes, "Location-Path", location.getPath());
			addAttribute(attributes, "Location-FirstSegment", location.getFirstSegment());
			Optional<Location> subLocation = location.getSubLocation();
			if(subLocation.isPresent()) {
				Location sub = subLocation.get();
				addAttribute(attributes, "SubLocation-Path", sub.getPath());
				addAttribute(attributes, "SubLocation-FirstSegment", sub.getFirstSegment());
			}
		}
	}
}
