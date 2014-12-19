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
package com.jain.addon.action.listener;

import com.jain.addon.JNStyleConstants;
import com.jain.addon.action.JNAction;
import com.jain.addon.i18N.I18NHelper;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * <code>JNCommandListener<code> Default command listener for all the actions
 * @author Lokesh Jain
 * @since December 5, 2012
 * @version 1.1.0
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public final class JNCommandListener <T> extends JNActionListener<T> implements Command {
	private MenuItem currentAction;
	private MenuBar menuBar;

	/**
	 * @param actionHandler
	 * @param params
	 */
	public JNCommandListener(MenuBar menuBar, T actionHandler, Object ... params) {
		this(menuBar, true, actionHandler, params);
	}

	/**
	 * @param populate - Do we need to process annotations by default
	 * @param actionHandler - Object should have some methods annotated with {@link JNAction}
	 * @param params 
	 */
	public JNCommandListener(MenuBar menuBar, boolean populate, T actionHandler, Object ... params) {
		super(populate, actionHandler, params);
		this.menuBar = menuBar;
	}

	public void menuSelected(MenuItem selectedItem) {
		if (this.currentAction != null)
			this.currentAction.setStyleName(JNStyleConstants.J_ACTION);
		
		this.currentAction = selectedItem;
		this.currentAction.setStyleName(JNStyleConstants.J_SELECTED_ACTION);
		
		String actionName = I18NHelper.getKey(menuBar, currentAction);
		invokeAction(actionName, menuBar.getUI());
	}
}
