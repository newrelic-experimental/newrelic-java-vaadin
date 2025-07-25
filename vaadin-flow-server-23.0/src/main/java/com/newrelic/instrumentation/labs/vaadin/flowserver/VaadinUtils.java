package com.newrelic.instrumentation.labs.vaadin.flowserver;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Stream;

import com.newrelic.api.agent.NewRelic;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.LocationUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinContext;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletContext;
import com.vaadin.flow.shared.ApplicationConstants;

import javax.servlet.ServletContext;

public class VaadinUtils {

	public static final String NULL_STRING = "Null";
	public static final String NO_ROUTE_PATH = "No Route Path";
	public static final String ROOT_PATH = "RootPath";

	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Attempting to add attribute ({0}, {1}) ",key,value);
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

	public static void addDomEvent(Map<String,Object> attributes, DomEvent event) {
		if(event != null) {
			addAttribute(attributes, "DomEvent-Type", event.getType());
			addAttribute(attributes, "DomEvent-Phase", event.getPhase().name());
			addElement(attributes, event.getSource());
		}
	}

	public static void addElement(Map<String, Object> attributes, Element element) {
		if(element != null) {
			addAttribute(attributes, "Element-Tag", element.getTag());
			addAttribute(attributes, "Element-Text", element.getText());
			addAttribute(attributes, "Element-OuterHTML", element.getOuterHTML());
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

	public static String getRoutePath(Component component) {
		VaadinContext context = VaadinService.getCurrent().getContext();

		String contextPath = null;
		if(context instanceof VaadinServletContext) {
			ServletContext servletCtx = ((VaadinServletContext)context).getContext();
			contextPath = servletCtx.getContextPath();
		}

		Component current = component;
		while(current != null) {
			Class<?> componentClass = current.getClass();
			if(hasRoute(componentClass)) {
				String routePath = RouteUtil.getRoutePath(context, componentClass);
				if(routePath == null) {
					return NULL_STRING;
				} else {
					if(routePath.isEmpty()) {
						return contextPath != null ? contextPath + "/" : ROOT_PATH;
					} else {
						return contextPath != null ? contextPath + "/" + routePath : routePath;
					}
				}
			} else {
				Optional<Component> parent = current.getParent();
				if(parent.isPresent()) {
					current = parent.get(); 
				} else {
					current = null;
				}
			}
		}

		return NO_ROUTE_PATH;
	}

	public static boolean hasRoute(Class<?> clazz) {
		Route route = clazz.getAnnotation(Route.class);
		return route != null;
	}

	public static String[] nameJSBootstrapTransaction(VaadinRequest request) {
		String pathAndParams = request.getParameter(ApplicationConstants.REQUEST_LOCATION_PARAMETER);
		if(pathAndParams == null) return null;

		List<String> pathSegments = LocationUtil.parsePathToSegments(pathAndParams);
		
		String[] txnNames = new String[pathSegments.size()+2];
		
		txnNames[0] = "Vaadin";
		txnNames[1] = "JavaScriptBootstrapHandler";
		int index = 2;
		for(String segment : pathSegments) {
			txnNames[index] = segment;
			index++;
		}

		return txnNames;
	}

	public static String[] nameBootstrapTransaction(VaadinRequest request) {
		String contextPath = request.getContextPath();
		if(contextPath.startsWith("/")) contextPath = contextPath.substring(1);
		String pathInfo = request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		List<String> names = new ArrayList<String>();
		names.add("Vaadin");
		names.add("BootstrapHandler");
		if(contextPath != null && !contextPath.isEmpty()) {
			names.add(contextPath);
		}
		if(pathInfo != null && !pathInfo.isEmpty()) {
			names.add(pathInfo);
		}
		String[] txnNames = new String[names.size()];
		names.toArray(txnNames);

		return txnNames;
	}
	
	public static void dumpComponent(Component component) {
		LinkedHashSet<String> parents = new LinkedHashSet<String>();
		Component current = component;
		while(current != null) {
			Optional<Component> parentOp = current.getParent();
			if(parentOp.isPresent()) {
				Component parent = parentOp.get();
				parents.add(parent.getClass().getName());
				current = parent;
			} else {
				current = null;
			}
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, "Parents of {0}: {1}", component.getClass().getName(), parents.toString());
		current = component;
		Stream<Component> children = component.getChildren();
		Iterator<Component> childIterator = children.iterator();
		while(childIterator.hasNext()) {
			Component child = childIterator.next();
			NewRelic.getAgent().getLogger().log(Level.FINE, "Child of {0}: {1}", component.getClass().getName(), child.getClass().getName());
			
		}
		
	}

	public static void dumpComponentWithSource(Component component, String source) {
		LinkedHashSet<String> parents = new LinkedHashSet<String>();
		Component current = component;
		while(current != null) {
			Optional<Component> parentOp = current.getParent();
			if(parentOp.isPresent()) {
				Component parent = parentOp.get();
				parents.add(parent.getClass().getName());
				current = parent;
			} else {
				current = null;
			}
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, source + " " + "Parents of {0}: {1}", component.getClass().getName(), parents.toString());
		current = component;
		Stream<Component> children = component.getChildren();
		Iterator<Component> childIterator = children.iterator();
		while(childIterator.hasNext()) {
			Component child = childIterator.next();
			NewRelic.getAgent().getLogger().log(Level.FINE,source + " " +  "Child of {0}: {1}", component.getClass().getName(), child.getClass().getName());
			
		}
		
	}

}
