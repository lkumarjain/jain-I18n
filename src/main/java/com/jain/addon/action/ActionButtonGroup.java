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

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.StringHelper;
import com.jain.addon.action.listener.JNButtonClickListener;
import com.jain.addon.authentication.JNILoginListner;
import com.jain.addon.resource.PropertyReader;
import com.jain.addon.security.JNISecured;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;

/**
 * <code>ActionButtonGroup<code> is a horizontal segment component for button action group.<br/>
 * @author Lokesh Jain
 * @since November 25, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class ActionButtonGroup <T> extends ActionGroup<T> implements JNILoginListner {
	private final JNButtonClickListener<T> listener;

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param listener
	 */
	public ActionButtonGroup(JNISecured secured, JNButtonClickListener<T> listener) {
		this(secured, listener, JNStyleConstants.J_FIRST_ACTION, JNStyleConstants.J_LAST_ACTION, JNStyleConstants.J_ACTION);
	}

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param listener
	 * @param actionStyle
	 */
	public ActionButtonGroup(JNISecured secured, JNButtonClickListener<T> listener, String actionStyle) {
		this(secured, listener, actionStyle, actionStyle, actionStyle);
	}

	/**
	 * Create a segment instance having first {@link Button} and last {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param listener
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionButtonGroup(JNISecured secured, JNButtonClickListener<T> listener, String firstActionStyle, String lastActionStyle) {
		this(secured, listener, firstActionStyle, lastActionStyle, null);
	}

	/**
	 * Create a segment instance having first {@link Button}, last {@link Button} and {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param listener
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionButtonGroup(JNISecured secured, JNButtonClickListener<T> listener, String firstActionStyle, String lastActionStyle, String actionStyle) {
		super(secured, firstActionStyle, lastActionStyle, actionStyle);
		this.listener = listener;
		setStyleName(JNStyleConstants.J_ACTION_BUTTON_GROUP);
	}

	/**
	 * Method to initialize component
	 */
	@JNIComponentInit
	public void initActions() {
		if (!initialized) {
			int i = 0;
			Button actionButton = null;
			for (JNAction action : actions) {
				actionButton = new Button(actionsToName.get(action), listener);

				if (StringHelper.isNotEmptyWithTrim(action.description()))
					actionButton.setDescription(action.description());
				else
					actionButton.setDescription(actionsToName.get(action));

				actionButton.setVisible(validatePermission (action));

				findNAddIcon(action, actionButton);

				if (StringHelper.isNotEmptyWithTrim(getActionStyle()))
					actionButton.setStyleName(getActionStyle());

				if(i == 0)
					actionButton.addStyleName(getFirstActionStyle());

				i ++;
				addComponent(actionButton);
				setComponentAlignment(actionButton, Alignment.TOP_RIGHT);
			}
			if (actionButton != null)
				actionButton.addStyleName(getLastActionStyle());
			initialized = true;
		} else {
			throw new IllegalArgumentException ("Please add action before adding component into container");
		}
	}


	private void findNAddIcon(JNAction action, Button actionButton) {
		if (StringHelper.isNotEmptyWithTrim(action.icon())) {
			String iconPath = PropertyReader.instance().getProperty(action.icon());

			if (StringHelper.isNotEmptyWithTrim(iconPath)) {
				ThemeResource icon = new ThemeResource(iconPath);
				actionButton.setIcon(icon);
			}
		}
	}

	public boolean isShowSelectedAction() {
		return listener.isShowSelectedAction();
	}

	public void setShowSelectedAction(boolean showSelectedAction) {
		this.listener.setShowSelectedAction(showSelectedAction);
	}
}
