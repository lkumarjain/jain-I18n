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
 * <code>JNActionGroup<code> Every Action provider having a Group should be annotated with this annotation.
 * Once you annotate your action provider  with this annotation, {@link ActionBar} or {@link ActionMenuBar} shows all these actions in same group.
 * @author Lokesh Jain
 * @since December 2, 2012
 * @version 1.1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNActionGroup {
	/**
	 * This name is the action group name
	 */
	String name() default "";

	/**
	 * This icon is the action group icon.
	 */
	String icon() default "";

	/**
	 * This visibility permission for the action group.
	 */
	String permission() default "";

	/**
	 * This value is the action group description.
	 * Default value is name
	 */
	String description() default "";
	
	/**
	 * Parent action group name for an action Group
	 * This will be considered only for the Menu bar {@link ActionMenuBar}
	 * Action bar {@link ActionBar} ignores this parameter  
	 */
	String parent () default "";
	
	/**
	 * Override first action style for this action Group 
	 */
	String firstActionStyle() default "";

	/**
	 * Override last action style for this action Group 
	 */
	String lastActionStyle() default "";

	/**
	 * Override action style for this action Group 
	 */
	String actionStyle() default "";
	
	/**
	 * Override action style for this action Group 
	 */
	String style() default "";
}
