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
package com.jain.addon.i18N.component;

import java.util.Locale;

import com.jain.addon.i18N.listners.JAttachDetachListner;
import com.jain.addon.event.EventHandler;
import com.jain.addon.i18N.I18NChangeListener;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.resource.DefaultI18NResourceProvider;
import com.jain.addon.resource.I18NProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * <code>I18NUI<code> is an abstract root component with some basic 18n configuration.<br/>
 * Every application should implement this class to create there root instance 
 * because this include some configuration related to window attachment, 
 * detachment and listeners required for the i18N support.    
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public abstract class I18NUI extends UI {
	private final I18NChangeListener localChangeListener;
	private final EventHandler eventHandler;
	
	public I18NUI() {
		this.localChangeListener = I18NHelper.findListener(this, true);
		this.eventHandler = new EventHandler();
	}
	
	protected void init(VaadinRequest request) {
		JAttachDetachListner attachListener = new JAttachDetachListner();
		addComponentAttachListener(attachListener);
		addComponentDetachListener(attachListener);

		initialize (request);
	}

	public void addWindow(Window window) throws IllegalArgumentException, NullPointerException {
		super.addWindow(window);
		fireComponentAttachEvent(window);
	}
	
	public boolean removeWindow(Window window) {
		boolean removed = super.removeWindow(window);
		fireComponentDetachEvent(window);
		return removed;
	}
	
	/**
	 * Override this method in UI implementation to provide your own I18NProvider.
	 * @see {@link I18NProvider}, {@link DefaultI18NResourceProvider}
	 * @return I18NProvider
	 */
	public I18NProvider getI18nProvider() {
		return DefaultI18NResourceProvider.instance();
	}
	
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		localChangeListener.localeChanged(this);
	}

	public I18NChangeListener getLocalChangeListener() {
		return localChangeListener;
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	/**
	 * Initializes this UI. This method is intended to be overridden by subclasses to build the view 
	 * and configure non-component functionality. Performing the initialization in a 
	 * constructor is not suggested as the state of the UI is not properly 
	 * set up when the constructor is invoked.
	 * This method is invoked from the Vaadin init method.
	 * @see {@link UI#init}
	 * @param request the Vaadin request that caused this UI to be created
	 */
	protected abstract void initialize (VaadinRequest request);
}
