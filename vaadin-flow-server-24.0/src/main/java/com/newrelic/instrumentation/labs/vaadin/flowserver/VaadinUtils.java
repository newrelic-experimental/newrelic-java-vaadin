package com.newrelic.instrumentation.labs.vaadin.flowserver;

import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.server.VaadinContext;
import com.vaadin.flow.server.VaadinRequest;

public class VaadinUtils {

	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Attempting to add attribute ({0}, {1}) to {2}",key,value,attributes);
		if(value != null ) {
			if(attributes != null && key != null && !key.isEmpty()) {
				attributes.put(key, value);
			}
		} else if(attributes != null && key != null && !key.isEmpty()) {
			attributes.put(key, "null");
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
	
	public static void addVaadinContext(Map<String,Object> attributes, VaadinContext context) {
		if(context != null) {
			Enumeration<String> contextNames = context.getContextParameterNames();
			while(contextNames.hasMoreElements()) {
				String contextName = contextNames.nextElement();
				addAttribute(attributes, "VaadinContext-" + contextName, context.getContextParameter(contextName));
			}
		}
	}
	
	public static void addVaadinRequest(Map<String,Object> attributes, VaadinRequest request) {
		if(request != null) {
			String contentType = request.getContentType();
			addAttribute(attributes, "contentType", contentType);
			String contextPath = request.getContextPath();
			addAttribute(attributes, "contextPath", contextPath);
			Enumeration<String> headerNames = request.getHeaderNames();
			while(headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				Enumeration<String> values = request.getHeaders(headerName);
				int count = 1;
				while(values.hasMoreElements()) {
					addAttribute(attributes, "headerName" + "-" + headerName + "-" + count, values.nextElement());
					count++;
				}
			}

			Enumeration<String> attributeNames = request.getAttributeNames();
			while(attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				Enumeration<String> values = request.getHeaders(attributeName);
				int count = 1;
				while(values.hasMoreElements()) {
					addAttribute(attributes, "attributeName" + "-" + attributeName + "-" + count, values.nextElement());
					count++;
				}
			}
			
			String method = request.getMethod();
			addAttribute(attributes, "Method", method);
			String pathInfo = request.getPathInfo();
			addAttribute(attributes, "PathInfo", pathInfo);
			
			
		}
	}
}
