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
package com.jain.addon.resource;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.jain.addon.JNINamed;
import com.jain.addon.resource.I18NProvider;

/**
 * <code>DefaultI18NResourceProvider<code> is a default i18N provider.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class DefaultI18NResourceProvider implements I18NProvider {
	private Map<String, Properties> propsForLocale;
	private static I18NProvider instance;

	/**
	 * Get the instance of the I18N provider from the application
	 * @return {@link I18NProvider}
	 */
	public static I18NProvider instance () {
		if (instance == null)
			synchronized (DefaultI18NResourceProvider.class) {
				if (instance == null) {
					instance = new DefaultI18NResourceProvider();
				}
			}
		return instance;
	}

	private DefaultI18NResourceProvider() {
		propsForLocale = new HashMap<String, Properties> ();
		getProperties(null);
	}

	private Properties getProperties(String locale) {
		Properties properties = propsForLocale.get(locale);
		if(properties == null || properties.isEmpty()) {
			String file = "/messages" + (locale == null ? BLANK : ("_" + locale)) + ".properties";
			properties = new Properties();
			try {
				InputStream stream = DefaultI18NResourceProvider.class.getResourceAsStream(file);
				if(stream != null) {
					properties.load(stream);
					propsForLocale.put(locale, properties);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(properties == null || properties.isEmpty()) {
				properties = propsForLocale.get(null);
			}
		}
		return properties;
	}

	public String getText(Locale locale, String identifier, Object... params) {
		return getText(locale, identifier, BLANK, params);
	}

	public String getTitle(Locale locale, String identifier, Object... params) {
		String value = getText(locale, identifier, TITLE_KEY, params);
		if(identifier.equals(value))
			value = getText(locale, identifier, params); 
		return value;
	}

	public String getMessage(Locale locale, String identifier, Object... params) {
		String value = getText(locale, identifier, MESSAGE_KEY, params);
		if(identifier.equals(value))
			value = getText(locale, identifier, params); 
		return value;
	}

	public String getProperty(Locale locale, String key, String defaultValue) {
		Properties properties = getProperties(locale == null ? null : locale.getLanguage());
		if(properties != null) {
			return properties.getProperty(key, defaultValue);
		}
		return defaultValue;
	}

	public String getProperty(Locale locale, String key) {
		return getProperty(locale, key, key);
	}

	public String getText(Locale locale, String identifier, String sufix, Object... params) {
		Object[] parsedParams = parseParams(locale, identifier, params);
		
		String defaultValue = identifier;
		
		if(identifier.contains(KEY_SPLITTER))
			identifier = identifier.substring(0, identifier.indexOf(KEY_SPLITTER));

		String result = getProperty(locale, identifier + sufix, defaultValue);
		return MessageFormat.format(result, parsedParams);
	}

	private Object[] parseParams(Locale locale, String identifier, Object... params) {
		if(params == null || params.length == 0) {
			if(identifier.contains(KEY_SPLITTER)) {
				String [] identifiers = identifier.split(KEY_SPLITTER);

				Object [] parsedParams = new String [identifiers.length - 1];

				for (int i = 1; i < identifiers.length; i++) {
					parsedParams [i - 1] = getProperty(locale, identifiers [i]);
				}

				return parsedParams;
			}
			return null;
		}

		Object [] parsedParams = new String [params.length];

		for (int i = 0; i < params.length; i++) {
			Object object = params [i];
			if (object instanceof JNINamed)
				parsedParams [i] = ((JNINamed) object).getDisplayName();
			else if (object instanceof String)
				parsedParams [i] = getProperty(locale, (String)object);
			else 
				parsedParams [i] = object;
		}
		return parsedParams;
	}
}
