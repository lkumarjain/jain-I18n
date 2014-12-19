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
import com.jain.addon.i18N.component.I18NUI;
import com.jain.addon.resource.I18NProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * <code>I18NComponentHandler<code> is default a i18N handler for all the {@link Component}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NComponentHandler implements Serializable {
	private final String i18NCaption;
	protected I18NProvider provider;

	public I18NComponentHandler(final Component component) {
		this(component.getCaption());
		provider = ((I18NUI)component.getUI()).getI18nProvider();
	}

	public I18NComponentHandler(final String i18NCaption) {
		this.i18NCaption = i18NCaption;
	}

	public String getCaption(Locale locale) {
		if (StringHelper.isNotEmptyWithTrim(i18NCaption)) {
			String value = provider.getTitle(locale, i18NCaption);
			return value;
		}
		return null;
	}

	public void applyI18N(Component component, Locale locale) {
		if (provider == null)
			provider = ((I18NUI)component.getUI()).getI18nProvider();

		if(component instanceof Label) {
			((Label) component).setValue(getCaption(locale));
		} else { 
			component.setCaption(getCaption(locale));
		}
	}

	public String getI18NCaption() {
		return i18NCaption;
	}
	
	public String getI18NCaption(Serializable serializable) {
		return getI18NCaption ();
	}
}
