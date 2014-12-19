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

import com.jain.addon.i18N.component.I18NUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.UI;

/**
 * <code>I18NHelper<code> is a helper class for the i18N component handling.
 * This is also having couple of helping methods.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class I18NHelper implements Serializable {
	private I18NHelper () {}

	/**
	 * Method to get i18N caption for given component 
	 * @param {@link Component}
	 * @return i18NCaption
	 */
	public static String getKey (Component component) {
		I18NChangeListener listener =  findListener(component, false);
		if (listener != null) 
			return listener.getI18NCaption(component);
		return component.getCaption();
	}
	
	/**
	 * Method to get i18N caption for given component 
	 * @param {@link Component}
	 * @return i18NCaption
	 */
	public static String getKey (Component component, Serializable serializable) {
		I18NChangeListener listener =  findListener(component, false);
		if (listener != null) 
			return listener.getI18NCaption(component, serializable);
		return component.getCaption();
	}

	/**
	 * Register component for i18N handling.
	 * @param {@link Component}
	 * @param {@link UI}
	 */
	public static void register(UI ui, Component component) {
		I18NChangeListener listener =  findListener(ui, true);
		listener.registor(component);
	}

	/**
	 * Remove registered component for i18N handling.
	 * @param {@link Component}
	 * @param {@link UI}
	 */
	public static void deRegistor(UI ui, Component component) {
		I18NChangeListener listener =  findListener(ui, true);
		listener.deRegistor(component);
	}

	/**
	 * Method to add Value change listener for a locale drop down 
	 * because complete i18N works with this listener.  
	 * @param {@link UI}
	 * @param {@link Field}
	 */
	public static void addListener (UI ui, Field<?> field) {
		I18NChangeListener listener =  findListener(ui, true);
		field.addValueChangeListener(listener);
	}

	/**
	 * Method to find I18NChangeListener or create an instance if this is not available
	 * @param component
	 * @param create
	 * @return {@link I18NChangeListener}
	 */
	public static I18NChangeListener findListener (Component component, boolean create) {
		I18NChangeListener listener = null;
		
		if (component instanceof I18NUI) {
			listener = ((I18NUI) component).getLocalChangeListener();
			if (listener != null)
				return listener;
		}
		
		if (component.getUI() instanceof I18NUI) {
			listener = ((I18NUI) component.getUI()).getLocalChangeListener();
			if (listener != null)
				return listener;
		}
		
		if (create) {
			listener = new I18NChangeListener();
			return listener;
		}

		return listener;
	}
}
