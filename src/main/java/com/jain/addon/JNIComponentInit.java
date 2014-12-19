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
package com.jain.addon;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre> 
 * <code>JNIComponentInit<code> is an annotation used for component initialization.
 * All component initialization methods should be annotated by this annotation before 
 * registering for the UI component. 
 * Instead of initializing component UI in the constructor, use this annotation.
 * This annotation provides you better approach for accessing the UI 
 * and other pre-initialized objects along with CDI injected components.
 * </pre>
 * @author Lokesh Jain
 * @since Oct 27, 2012
 * @version 1.1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JNIComponentInit {

}
