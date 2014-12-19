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
package com.jain.addon.i18N;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.jain.addon.i18N.handlers.I18NComponentHandler;
import com.jain.addon.i18N.handlers.factory.I18NComponentHandlerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.HasComponents;

/**
 * <code>I18NChangeListener<code> is a default listener provided for the locale change.
 * This is implemented as value change listener in the application 
 * because most of the application uses drop down for local selection.
 * I case you are using some other way, call value change method to invoke all locale change events.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NChangeListener implements ValueChangeListener {
	private Map<Component, I18NComponentHandler> componentMap;
	private Locale currentLocale;

	protected I18NChangeListener () {
		this.componentMap = new HashMap <Component, I18NComponentHandler> ();
		this.currentLocale = Locale.getDefault();
	}

	public void localeChanged (Component component) {
		if (currentLocale != component.getUI().getLocale()) {
			currentLocale = component.getUI().getLocale();

			updateComponents(component);
		}
	}

	private void updateComponents(Component component) {
		if(component instanceof HasComponents) {
			HasComponents container = (HasComponents) component;

			for (Component containerComponent : container) {
				updateComponents(containerComponent);
			}
		}

		updateCaption(component);

		if (component instanceof I18NListener) {
			I18NChangeEvent event = new I18NChangeEvent(component, currentLocale);
			((I18NListener) component).localeChanged(event);
		}
	}

	private void updateCaption(Component component) {
		I18NComponentHandler handler = componentMap.get(component);

		if (handler != null) {
			handler.applyI18N(component, currentLocale);
		}
	}

	public void registor(Component component) {
		componentMap.put(component, I18NComponentHandlerFactory.getHandler(component));
		updateCaption(component);
	}

	public void deRegistor(Component component) {
		componentMap.remove(component);
	}

	public String getI18NCaption(Component component) {
		I18NComponentHandler handler = componentMap.get(component);
		if (handler != null) 
			return handler.getI18NCaption();
		return "";
	}
	
	public String getI18NCaption(Component component, Serializable serializable) {
		I18NComponentHandler handler = componentMap.get(component);
		if (handler != null) 
			return handler.getI18NCaption(serializable);
		return "";
	}

	public I18NComponentHandler getI18NComponentHandler(Component component) {
		I18NComponentHandler handler = componentMap.get(component);
		return handler;
	}

	
	public void valueChange(ValueChangeEvent valueChangeEvent) {
		Event event = (Event) valueChangeEvent;
		Locale selected = ((Locale) valueChangeEvent.getProperty().getValue());
		selected = selected == null ? Locale.getDefault() : selected;
		event.getComponent().getUI().setLocale(selected);
	}
}