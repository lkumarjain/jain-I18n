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
import com.jain.addon.component.upload.JUploader;
import com.vaadin.ui.Component;

/**
 * <code>I18NJUploadHandler<code> is a i18N values handler for {@link JUploader}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class I18NJUploadHandler extends I18NAbstractComponentHandler implements Serializable {
	private String i18NButtonCaption;

	public I18NJUploadHandler(final JUploader component) {
		super(component);
		this.i18NButtonCaption = component.getButtonCaption();
	}

	public void applyI18N(Component component, Locale locale) {
		super.applyI18N(component, locale);
		if(component instanceof JUploader) {
			((JUploader) component).setButtonCaption(getButtonCaption(locale));
		}
	}

	private String getButtonCaption(Locale locale) {
		if (StringHelper.isNotEmptyWithTrim(i18NButtonCaption)) {
			String value = provider.getMessage(locale, i18NButtonCaption);
			return value;
		}
		return null;
	}
}
