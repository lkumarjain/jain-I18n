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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jain.addon.JNStyleConstants;
import com.jain.addon.authentication.JNILoginListner;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.security.JNISecured;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>ActionGroup<code> is a horizontal segment component for action group.<br/>
 * @author Lokesh Jain
 * @since December 2, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public abstract class ActionGroup <T> extends HorizontalLayout implements JNILoginListner {
	protected List<JNAction> actions;
	protected Map<JNAction, String> actionsToName;
	private String firstActionStyle;
	private String lastActionStyle;
	private String actionStyle;
	private JNISecured secured;
	private JNActionGroup actionGroup;
	protected boolean initialized = false;

	/**
	 * Create a segment instance having first action, last action and action styles
	 * @param secured -- {@link JNISecured}
	 * @param firstActionStyle
	 * @param lastActionStyle
	 * @param actionStyle
	 */
	public ActionGroup(JNISecured secured, String firstActionStyle, String lastActionStyle, String actionStyle) {
		this.firstActionStyle = firstActionStyle;
		this.lastActionStyle = lastActionStyle;
		this.actionStyle = actionStyle;
		this.secured = secured;
		this.actions = new ArrayList<JNAction>();
		this.actionsToName = new HashMap<JNAction, String> (); 
		setStyleName(JNStyleConstants.J_ACTION_BAR);
	}

	/**
	 * Method to add actions to the action Group
	 * @param action
	 * @param actionName
	 */
	public void addAction(JNAction action, String actionName) {
		actionsToName.put(action, actionName);
		int position = findPosition(action);
		actions.add(position, action);
	}

	private int findPosition(JNAction action) {
		int i = 0;
		for (JNAction act : this.actions) {
			if (act.tabIndex() < action.tabIndex())
				i ++;
		}
		return i;
	}

	/**
	 * Reinitialize a Action Bar by updating resources {@link JNAction} visibility after login.<br/> 
	 */
	public void onLogin() {
		for (JNAction action : actions) {
			for (Iterator<Component> iterator = iterator(); iterator.hasNext();) {
				Component component = iterator.next();
				if (actionsToName.get(action).equalsIgnoreCase(I18NHelper.getKey(component))) {
					component.setVisible(validatePermission (action));
				}
			}
		}
		markAsDirty();
	}

	protected boolean validatePermission(JNAction action) {
		if (secured != null) {
			return secured.hasPermission(action.permission());
		}
		return true;
	}

	public String getFirstActionStyle() {
		return firstActionStyle;
	}

	public void setFirstActionStyle(String firstActionStyle) {
		this.firstActionStyle = firstActionStyle;
	}

	public String getLastActionStyle() {
		return lastActionStyle;
	}

	public void setLastActionStyle(String lastActionStyle) {
		this.lastActionStyle = lastActionStyle;
	}

	public String getActionStyle() {
		return actionStyle;
	}

	public void setActionStyle(String actionStyle) {
		this.actionStyle = actionStyle;
	}

	public JNISecured getSecured() {
		return secured;
	}

	public void setSecured(JNISecured secured) {
		this.secured = secured;
	}

	public JNActionGroup getActionGroup() {
		return actionGroup;
	}

	public void setActionGroup(JNActionGroup actionGroup) {
		this.actionGroup = actionGroup;
	}
}
