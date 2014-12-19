package com.jain.addon.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import com.jain.addon.StringHelper;

@SuppressWarnings("serial")
public class PropertyReader implements Serializable {
	private static PropertyReader instance;
	private Properties properties;

	private PropertyReader() {}

	/**
	 * Get the instance of the Icon provider from the application
	 * @return {@link PropertyReader}
	 */
	public static PropertyReader instance () {
		if (instance == null)
			synchronized (PropertyReader.class) {
				if (instance == null) {
					instance = new PropertyReader();
				}
			}
		return instance;
	}

	public String getProperty(String key, String defaultValue) {
		load();

		if(properties != null) {
			String value = properties.getProperty(key);  
			if (StringHelper.isNotEmptyWithTrim(value))
				return value;
		}
		return System.getProperty(key, defaultValue);
	}

	public String getProperty(String key) {
		return getProperty(key, null);
	}

	public void load () {
		if(properties == null) {
			try {
				String file = "/jaini18n.properties";
				properties = new Properties();
				InputStream stream = PropertyReader.class.getResourceAsStream(file);
				if(stream != null) {
					properties.load(stream);
				}
			} catch (IOException e) {
				properties = null;
				e.printStackTrace();
			}
		}
	}
}
