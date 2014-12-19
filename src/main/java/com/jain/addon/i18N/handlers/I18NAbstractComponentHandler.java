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
package com.jain.addon.i18N.handlers;

import java.io.Serializable;
import java.util.Locale;

import com.jain.addon.StringHelper;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * <code>I18NAbstractComponentHandler<code> is a i18N values handler for {@link AbstractComponent}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NAbstractComponentHandler extends I18NComponentHandler implements Serializable {
	private final String i18NDescription;

	public I18NAbstractComponentHandler(final AbstractComponent component) {
		super(component);
		this.i18NDescription = component.getDescription();
	}

	public String getDesctiption(Locale locale) {
		if (StringHelper.isNotEmptyWithTrim(i18NDescription)) {
			String value = provider.getMessage(locale, i18NDescription);
			return value;
		}
		return null;
	}
	
	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);
		if(component instanceof AbstractComponent) {
			((AbstractComponent) component).setDescription(getDesctiption(locale));
		}
	}

	public String getI18NDescription() {
		return i18NDescription;
	}
}
