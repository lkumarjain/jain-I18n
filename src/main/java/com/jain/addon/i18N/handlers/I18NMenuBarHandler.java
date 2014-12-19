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
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * <code>I18NMenuBarHandler<code> is a i18N values handler for {@link MenuBar}
 * @author Lokesh Jain
 * @since December 6, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class I18NMenuBarHandler extends I18NAbstractComponentHandler implements Serializable {
	private final HashMap<MenuItem, String> i18NItemCaptions;
	private final HashMap<MenuItem, String> i18NItemDescriptions;

	public I18NMenuBarHandler(final MenuBar component) {
		super(component);
		this.i18NItemCaptions = new HashMap <MenuItem, String>();
		this.i18NItemDescriptions = new HashMap<MenuItem, String>();
		populateI18NItemCaptions (component);
	}

	private void populateI18NItemCaptions(final MenuBar component) {
		Collection<MenuItem> items = (Collection<MenuItem>) component.getItems();
		processMenuItem(items);
	}

	private void processMenuItem(Collection<MenuItem> items) {
		if (items != null) {
			for (MenuItem itemId : items) {
				String i18NItemCaption = itemId.getText();
				i18NItemCaptions.put(itemId, i18NItemCaption);
				String i18NItemDescription = itemId.getDescription();
				i18NItemDescriptions.put(itemId, i18NItemDescription);
				processMenuItem(itemId.getChildren());
			}
		}
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);
		for (Entry<MenuItem, String> entry : i18NItemCaptions.entrySet()) {
			if (StringHelper.isNotEmptyWithTrim(entry.getValue())) {
				String value = provider.getTitle(locale, entry.getValue());
				entry.getKey().setText(value);
			}
		}

		for (Entry<MenuItem, String> entry : i18NItemDescriptions.entrySet()) {
			if (StringHelper.isNotEmptyWithTrim(entry.getValue())) {
				String value = provider.getTitle(locale, entry.getValue());
				entry.getKey().setDescription(value);
			}
		}
	}

	public String getI18NCaption(Serializable serializable) {
		String i18NItemCaption = i18NItemCaptions.get(serializable);
		if (StringHelper.isNotEmptyWithTrim(i18NItemCaption))
			return i18NItemCaption;
		return getI18NCaption ();
	}
}
