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

import com.jain.addon.JNIComponentInit;
import com.jain.addon.i18N.I18NHelper;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

/**
 * <code>I18NSelector<code> a I18N language selector, with basic configuration of having listeners. 
 * @author Lokesh Jain
 * @since Aug 30, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NSelector extends ComboBox {
	
	@JNIComponentInit
	public void init () {
		I18NHelper.addListener(getUI(), this);
		setImmediate(true);
	}

	/**
	 * Add {@link Locale} with there display names
	 * @param locale
	 * @param displayName
	 * @return {@link Item}
	 */
	public Item addItem(Locale locale, String displayName) {
		Item item = addItem(locale);
		setItemCaption(locale, displayName);
		return item;
	}
}
