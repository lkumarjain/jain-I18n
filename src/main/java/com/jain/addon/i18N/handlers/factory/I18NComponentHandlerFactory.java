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
package com.jain.addon.i18N.handlers.factory;

import java.util.HashMap;
import java.util.Map;

import com.jain.addon.component.upload.JUploader;
import com.jain.addon.i18N.handlers.I18NAbstractComponentHandler;
import com.jain.addon.i18N.handlers.I18NAbstractSelectHandler;
import com.jain.addon.i18N.handlers.I18NComponentHandler;
import com.jain.addon.i18N.handlers.I18NFieldHandler;
import com.jain.addon.i18N.handlers.I18NJUploadHandler;
import com.jain.addon.i18N.handlers.I18NLableHandler;
import com.jain.addon.i18N.handlers.I18NMenuBarHandler;
import com.jain.addon.i18N.handlers.I18NTabSheetHandler;
import com.jain.addon.i18N.handlers.I18NTableHandler;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;

/**
 * <code>I18NComponentHandlerFactory<code> is a factory class to 
 * find appropriate i18N component handler based on the given component.  
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public final class I18NComponentHandlerFactory {
	private static Map<Class <? extends Component>, I18NComponentHandler> componentHandler;

	/**
	 * Method to find I18NHandler for the component
	 * @param component
	 * @return {@link I18NComponentHandler}
	 */
	public static I18NComponentHandler getHandler (Component component) {
		if (componentHandler != null && component != null) {
			I18NComponentHandler handler = componentHandler.get(component.getClass());
			if (handler != null)
				return handler;
		}
		if (component instanceof Label)
			return new I18NLableHandler((Label)component);
		if (component instanceof AbstractTextField)
			return new I18NFieldHandler((AbstractTextField)component);
		if (component instanceof PopupDateField)
			return new I18NFieldHandler((PopupDateField)component);
		if (component instanceof Table)
			return new I18NTableHandler((Table)component);
		if (component instanceof AbstractSelect)
			return new I18NAbstractSelectHandler((AbstractSelect)component);
		if (component instanceof TabSheet || component.getParent() instanceof TabSheet)
			return new I18NTabSheetHandler(component);
		if  (component instanceof JUploader) 
			return new I18NJUploadHandler((JUploader) component);
		if (component instanceof MenuBar) 
			return new I18NMenuBarHandler((MenuBar) component);
		if (component instanceof AbstractComponent)
			return new I18NAbstractComponentHandler ((AbstractComponent)component);
		return  new I18NComponentHandler(component);
	}

	/**
	 * Method to register user defined Handler for I18N
	 * @param component
	 * @param handler
	 */
	public static void register(Class <? extends Component> component, I18NComponentHandler handler) {
		if (componentHandler == null) {
			componentHandler = new HashMap<Class<? extends Component>, I18NComponentHandler> ();
		}

		if (handler != null) {
			componentHandler.put(component, handler);
		} else {
			throw new IllegalArgumentException("Handler should be provided");
		}
	}

	/**
	 * Method to deRegister user defined Handler for I18N
	 * @param component
	 * @param handler
	 */
	public static void deRegister(Class <? extends Component> component) {
		if (componentHandler == null) {
			componentHandler = new HashMap<Class<? extends Component>, I18NComponentHandler> ();
		}

		componentHandler.remove(component);
	}
}
