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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * <code>JNButtonClickListener<code> Default click listener for all the actions
 * @author Lokesh Jain
 * @since Nov 27, 2012
 * @version 1.1.0
 * @param <T>
 *
 */
@SuppressWarnings("serial")
public final class JNButtonClickListener <T> extends JNActionListener<T> implements ClickListener {
	private Button currentAction;

	/**
	 * @param actionHandler
	 * @param params
	 */
	public JNButtonClickListener(T actionHandler, Object ... params) {
		this(true, actionHandler, params);
	}

	/**
	 * @param populate - Do we need to process annotations by default
	 * @param actionHandler - Object should have some methods annotated with {@link JNAction}
	 * @param params 
	 */
	public JNButtonClickListener(boolean populate, T actionHandler, Object ... params) {
		super(populate, actionHandler, params);
	}

	public void buttonClick(ClickEvent event) {
		if (this.currentAction != null)
			this.currentAction.removeStyleName(JNStyleConstants.J_SELECTED_ACTION);
		
		this.currentAction = event.getButton();
		this.currentAction.addStyleName(JNStyleConstants.J_SELECTED_ACTION);
		
		String actionName = I18NHelper.getKey(currentAction);
		invokeAction(actionName, currentAction.getUI());
	}
}
