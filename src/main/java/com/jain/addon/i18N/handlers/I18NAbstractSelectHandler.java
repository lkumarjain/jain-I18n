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
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import com.jain.addon.StringHelper;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;

/**
 * <code>I18NAbstractSelectHandler<code> is a i18N values handler for {@link AbstractSelect}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NAbstractSelectHandler extends I18NFieldHandler implements Serializable {
	private final HashMap<Object, String> i18NItemCaptions;

	public I18NAbstractSelectHandler(final AbstractSelect component) {
		super(component);
		this.i18NItemCaptions = new HashMap <Object, String>();
		populateI18NItemCaptions (component);
	}

	@SuppressWarnings("unchecked")
	private void populateI18NItemCaptions(final AbstractSelect component) {
		Collection<Object> items = (Collection<Object>) component.getItemIds();
		for (Object itemId : items) {
			String i18NItemCaption = component.getItemCaption(itemId);
			i18NItemCaptions.put(itemId, i18NItemCaption);
		}
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);
		AbstractSelect select = ((AbstractSelect)component);
		for (Entry<Object, String> entry : i18NItemCaptions.entrySet()) {
			if (StringHelper.isNotEmptyWithTrim(entry.getValue())) {
				String value = provider.getTitle(locale, entry.getValue());
				select.setItemCaption(entry.getKey(), value);
			}
		}
	}
}
