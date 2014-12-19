/* 
 * Copyright 2012 Lokesh Jain.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jain.addon.event;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre> 
 * <code>EventHandler<code> is a handler class for the the events. 
 * It registers the all the events for an object instance and calls back these object,
 * if that event occurs.
 * </pre>
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class EventHandler implements Serializable {
	private Map<String, Collection<MethodExpression>> events;
	
	public EventHandler() {
		this.events = new HashMap <String, Collection<MethodExpression>> ();
	}
	
	/**
	 * Registers all the observer methods marked as {@link JNIObserver} against the object instance.
	 * @param object -- where wee need to register event handler
	 */
	public void register (Object object) {
		if (object != null) {
			Method[]  methods = object.getClass().getMethods();
			for (Method method : methods) {
				JNIObserver observer = method.getAnnotation(JNIObserver.class);
				if(observer != null) {
					String[] value = observer.value();
					for (String event : value) {
						registor(event, new MethodExpression(object, method));
					}
				}
			}
		}
	}
	
	/**
	 * Remove registered observer methods marked as {@link JNIObserver} against the object instance.
	 * @param object -- where wee need to remove registered event from handler
	 */
	public void deRegistor (Object object) {
		if (object != null) {
			Method[]  methods = object.getClass().getMethods();
			for (Method method : methods) {
				JNIObserver observer = method.getAnnotation(JNIObserver.class);
				if(observer != null) {
					String[] value = observer.value();
					for (String event : value) {
						events.remove(event);
					}
				}
			}
		}
	}
	
	/**
	 * Return all listed {@link MethodExpression} for the given event   
	 * @param event
	 * @return
	 */
	public Collection<MethodExpression> getMethodExpressions(String event) {
		return events.get(event);
	}
	
	private void registor(String event, MethodExpression expression) {
		Collection<MethodExpression> expressions = events.get(event);
		
		if (expressions == null) {
			expressions = new ArrayList<MethodExpression> ();
			events.put(event, expressions);
		}
		
		expressions.add(expression);
	}
}
