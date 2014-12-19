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

import java.util.Locale;

import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Event;

/**
 * <code>I18NChangeEvent<code> is generated after the locale change.
 * All the i18NChange listener receive this event after locale change. 
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NChangeEvent extends Event {
	private Locale locale;
	
	public I18NChangeEvent(Component source) {
		super(source);
	}

	public I18NChangeEvent(Component source, Locale locale) {
		super(source);
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
