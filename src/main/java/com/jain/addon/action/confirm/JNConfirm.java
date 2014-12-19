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
package com.jain.addon.action.confirm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.shared.ui.label.ContentMode;

/**
 * <code>JNConfirm<code> action should be annotated with this annotation if confirmation is required.
 * @author Lokesh Jain
 * @since Jan 01, 2013
 * @version 1.1.2
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNConfirm {
	/**
	 * This title will be shown on confirmation dialog 
	 * for an action on which the given method is needs to be invoked.
	 */
	String title () default "";
	
	/**
	 * This message will be shown in confirmation dialog 
	 * for an action on which the given method is needs to be invoked.
	 */
	String message () default "";
	
	String icon () default "";
	
	ContentMode mode () default ContentMode.HTML;
}
