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

import com.jain.addon.StringHelper;
import com.jain.addon.i18N.I18NItemDescriptionGenerator;
import com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

/**
 * <code>I18NTableHandler<code> is a i18N values handler for {@link Table}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NTableHandler extends I18NAbstractComponentHandler implements Serializable {
	private final String [] i18NHeaders;
	private final HashMap<Object, String> i18NcolumnFooters;

	public I18NTableHandler(final Table component) {
		super(component);
		this.i18NHeaders = component.getColumnHeaders();
		this.i18NcolumnFooters = new HashMap<Object, String> ();
		calculateFooters(component);
	}

	private void calculateFooters(final Table component) {
		Object [] visibleColumns = component.getVisibleColumns();

		for (Object propertyId : visibleColumns) {
			if (component.getColumnFooter(propertyId) != null)
				i18NcolumnFooters.put(propertyId, component.getColumnFooter(propertyId));
		}
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);
		Table table = ((Table) component);
		table.setColumnHeaders(getHeaders (locale));

		Object [] visibleColumns = table.getVisibleColumns();

		for (Object propertyId : visibleColumns) {
			table.setColumnFooter(propertyId, getColumnFooter(locale, propertyId));
		}

		ItemDescriptionGenerator descriptionGenerator = table.getItemDescriptionGenerator();

		if (descriptionGenerator != null && descriptionGenerator instanceof I18NItemDescriptionGenerator) {
			I18NItemDescriptionGenerator generator = (I18NItemDescriptionGenerator) descriptionGenerator;

			if (generator.getLocale() != locale) {
				table.setItemDescriptionGenerator(null);
				generator.setLocale(locale);
				table.setItemDescriptionGenerator(generator);
			}
		}
	}

	public String [] getHeaders(Locale locale) {
		String [] convertedHeaders = new String [i18NHeaders.length];

		for (int i = 0; i < convertedHeaders.length; i++) {
			String header = i18NHeaders[i];
			String value = header != null ? provider.getTitle(locale, header) : null;
			convertedHeaders [i] = value;
		}
		return convertedHeaders;
	}

	public String getColumnFooter(Locale locale, Object propertyId) {
		String i18NColumnFooter = getI18NColumnFooter (propertyId); 
		if (StringHelper.isNotEmptyWithTrim(i18NColumnFooter)) {
			String value = provider.getTitle(locale, i18NColumnFooter);
			return value;
		}
		return null;
	}

	public String [] getI18NHeaders() {
		return i18NHeaders;
	}

	public String getI18NColumnFooter(Object propertyId) {
		return i18NcolumnFooters.get(propertyId);
	}
}
