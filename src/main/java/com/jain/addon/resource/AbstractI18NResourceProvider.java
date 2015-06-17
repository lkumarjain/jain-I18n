package com.jain.addon.resource;

import java.text.MessageFormat;
import java.util.Locale;

import com.jain.addon.JNINamed;

@SuppressWarnings("serial")
public abstract class AbstractI18NResourceProvider implements I18NProvider {

	public AbstractI18NResourceProvider() {
		super();
	}
	
	public abstract String getProperty(Locale locale, String key, String defaultValue);
	
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