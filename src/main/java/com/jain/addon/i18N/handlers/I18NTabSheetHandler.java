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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.jain.addon.StringHelper;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;

/**
 * <code>I18NTabSheetHandler<code> is a i18N values handler for {@link TabSheet}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NTabSheetHandler extends I18NComponentHandler implements Serializable {
	private Map<Tab, String> tabMap;

	public I18NTabSheetHandler(final Component component) {
		super(component);
		this.tabMap = new HashMap<Tab, String> ();
		initialize(component);
	}

	private void initialize(final Component component) {
		if (component instanceof TabSheet) {
			TabSheet sheet = (TabSheet) component;
			for (Component c : sheet) {
				Tab tab = sheet.getTab(c);
				tabMap.put(tab, tab.getCaption());
			}
		} else if (component.getParent() instanceof TabSheet) {
			TabSheet sheet = (TabSheet) component.getParent();
			Tab tab = sheet.getTab(component);
			tabMap.put(tab, tab.getCaption());
		}
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);

		for (Entry<Tab, String> entry : tabMap.entrySet()) {
			if (StringHelper.isNotEmptyWithTrim(entry.getValue())) {
				String value = provider.getTitle(locale, entry.getValue());
				entry.getKey().setCaption(value);
			}
		}
	}
}
