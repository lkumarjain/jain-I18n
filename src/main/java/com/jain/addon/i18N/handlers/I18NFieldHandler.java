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
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;

/**
 * <code>I18NFieldHandler<code> is a i18N values handler for {@link Field}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NFieldHandler extends I18NAbstractComponentHandler implements Serializable {
	private final String i18NInputPrompt;
	private final String i18NRequiredError;

	public I18NFieldHandler(final AbstractTextField component) {
		super(component);
		this.i18NInputPrompt = component.getInputPrompt(); 
		this.i18NRequiredError = component.getRequiredError();
	}

	public I18NFieldHandler(final AbstractSelect component) {
		super(component);
		if(component instanceof ComboBox)
			this.i18NInputPrompt = ((ComboBox) component).getInputPrompt();
		else
			this.i18NInputPrompt = null;
		this.i18NRequiredError = component.getRequiredError();
	}

	public I18NFieldHandler(PopupDateField component) {
		super(component);
		this.i18NInputPrompt = component.getInputPrompt();
		this.i18NRequiredError = component.getRequiredError();
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);

		if (component instanceof AbstractTextField) {
			((AbstractTextField) component).setInputPrompt(getInputPrompt(locale));
			((AbstractTextField) component).setRequiredError(getRequiredError(locale));
		} else if (component instanceof ComboBox) {
			((ComboBox) component).setInputPrompt(getInputPrompt(locale));
			((ComboBox) component).setRequiredError(getRequiredError(locale));
		} else if (component instanceof PopupDateField) {
			((PopupDateField) component).setInputPrompt(getInputPrompt(locale));
			((PopupDateField) component).setRequiredError(getRequiredError(locale));
		}
	}

	public String getInputPrompt(Locale locale) {
		if (StringHelper.isNotEmptyWithTrim(i18NInputPrompt)) {
			String value = provider.getTitle(locale, i18NInputPrompt);
			if(provider.isAutoDotToPrompt()){
				return value + " ... ";
			}
			return value;
		}
		return "";
	}
	
	public String getRequiredError(Locale locale) {
		if (StringHelper.isNotEmptyWithTrim(i18NRequiredError)) {
			String value = provider.getTitle(locale, i18NRequiredError, getCaption(locale));
			return value;
		}
		return "";
	}
	
	public String getI18NInputPrompt() {
		return i18NInputPrompt;
	}
	
	public String getI18NRequiredError() {
		return i18NRequiredError;
	}
}
