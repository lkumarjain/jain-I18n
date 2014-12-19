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
package com.jain.addon.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>JNAction<code> Every action should be annotated with this annotation.
 * Once you annotate your action with this annotation, ActionBar shows all these actions.
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNAction {
	/**
	 * This name is the action name, on which the given method is needs to be invoked.
	 * default action name will be your method name
	 */
	String name() default "";
	
	/**
	 * This icon is the action icon.
	 */
	String icon() default "";
	
	/**
	 * This visibility permission for the action icon.
	 */
	String permission() default "";
	
	/**
	 * This value is the action description.
	 * Default value is Value
	 */
	String description() default "";
	
	/**
	 * This value is the action tab index or Order.
	 */
	int tabIndex() default -1;
	
	/**
	 * Define action group name where this action belongs to. 
	 */
	String actionGroup () default "";
	
	/**
	 * Adds a separator after just this action in Menu bar; {@link ActionMenuBar} 
	 */
	boolean separator () default false;
}
