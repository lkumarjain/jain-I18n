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

import java.io.Serializable;
import java.util.Locale;

/**
 * <code>I18NProvider<code> is an interface for the i18N provider.
 * You can provide your own i18N provider by implementing this interface.
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public interface I18NProvider extends Serializable {
	public static final String MESSAGE_KEY = ".message";
	public static final String TITLE_KEY = ".title";
	public static final String BLANK = "";
	public static final String KEY_SPLITTER = ":";
	
	public String getText(Locale locale, String identifier, Object ... params);
	public String getTitle(Locale locale, String identifier, Object ... params);
	public String getMessage(Locale locale, String identifier, Object ... params);
}
