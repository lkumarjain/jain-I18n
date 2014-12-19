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
package com.jain.addon.security;

import java.io.Serializable;

/**
 * <code>JNISecured<code> is a common way to provide security.
 * Your security provider should implement this interface
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.1.0
 */
public interface JNISecured extends Serializable {
	//Common Action Permissions
	String VIEW_ACTION_PERMISSION = "view.action.permission";
	String DELETE_ACTION_PERMISSION = "delete.action.permission";
	String EDIT_ACTION_PERMISSION = "edit.action.permission";
	String ADD_ACTION_PERMISSION = "add.action.permission";
	
	/**
	 * Method to check permission
	 * @param permission
	 * @return true if user has this permission else false
	 */
	boolean hasPermission (String permission);
}
