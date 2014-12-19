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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.StringHelper;
import com.jain.addon.action.listener.JNButtonClickListener;
import com.jain.addon.security.JNISecured;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>ActionBar<code> is a horizontal segment component for actions.<br/>
 * @author Lokesh Jain
 * @since November 25, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class ActionBar <T> extends HorizontalLayout {
	private static final String DEFAULT_ACTION_GROUP = "default-action-group";
	private Map<String, ActionGroup<T>> actionGroupByName;
	private List<ActionGroup<T>> actionGroups;
	private final String firstActionStyle;
	private final String lastActionStyle;
	private final String actionStyle;
	private final JNISecured secured; 
	private final JNButtonClickListener<T> listener;
	private boolean initialized = false;

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 */
	public ActionBar(JNISecured secured, T actionHandler) {
		this(secured, actionHandler, JNStyleConstants.J_FIRST_ACTION, JNStyleConstants.J_LAST_ACTION, JNStyleConstants.J_ACTION);
	}
	
	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param actionStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String actionStyle) {
		this(secured, actionHandler, actionStyle, actionStyle, actionStyle);
	}

	/**
	 * Create a segment instance having first {@link Button} and last {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String firstActionStyle, String lastActionStyle) {
		this(secured, actionHandler, firstActionStyle, lastActionStyle, null);
	}

	/**
	 * Create a segment instance having first {@link Button}, last {@link Button} and {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionBar(JNISecured secured, T actionHandler, String firstActionStyle, String lastActionStyle, String actionStyle) {
		this.firstActionStyle = firstActionStyle;
		this.lastActionStyle = lastActionStyle;
		this.actionStyle = actionStyle;
		this.secured = secured;
		this.listener = new JNButtonClickListener<T>(false, actionHandler); 
		this.actionGroupByName = new HashMap<String, ActionGroup<T>>();
		this.actionGroups = new ArrayList<ActionGroup<T>>();
		setStyleName(JNStyleConstants.J_ACTION_BAR);
	}

	/**
	 * Method to initialize component
	 */
	@JNIComponentInit
	public void initActionBar() {
		if (!initialized) {
			findActionGroups ();
			
			for (ActionGroup<T> actionGroup : this.actionGroups) {
				addComponent(actionGroup);
				setComponentAlignment(actionGroup, Alignment.TOP_RIGHT);
			}
			initialized = true;
		} else {
			throw new IllegalArgumentException ("Please add action before adding component into container");
		}
	}

	private void findActionGroups() {
		if (listener.getActionHandler() != null) {
			JNActionGroups groups = listener.getActionHandler().getClass().getAnnotation(JNActionGroups.class);
			if (groups != null) {
				for (JNActionGroup group : groups.actionGroups()) {
					if(group != null && StringHelper.isNotEmptyWithTrim(group.name())) {
						findOrCreateActionGroup(null, group);
					}
				}
			}

			JNActionGroup group = listener.getActionHandler().getClass().getAnnotation(JNActionGroup.class);
			if(group != null && StringHelper.isNotEmptyWithTrim(group.name())) {
				findOrCreateActionGroup(null, group);
			}
			
			Method[]  methods = listener.getActionHandler().getClass().getMethods();
			for (Method method : methods) {
				JNAction action = method.getAnnotation(JNAction.class);
				if(action != null) {
					ActionGroup<T> actionGroup = findOrCreateActionGroup (action.actionGroup(), group);
					String actionName = listener.addAction(action, method);
					actionGroup.addAction(action, actionName);
				}
			}
		}
	}

	private ActionGroup<T> findOrCreateActionGroup(String actionGrp, JNActionGroup group) {
		String actionGroupName = DEFAULT_ACTION_GROUP;

		if(group != null && StringHelper.isNotEmptyWithTrim(group.name())) {
			actionGroupName = group.name();
		} else if(StringHelper.isNotEmptyWithTrim(actionGrp)) {
			actionGroupName = actionGrp;
		}
		
		ActionGroup<T> actionGroup = actionGroupByName.get(actionGroupName);
		
		if (actionGroup == null) {
			actionGroup = createActionGroup(group);
			actionGroupByName.put(actionGroupName, actionGroup);
			actionGroups.add(actionGroup);
		}
		return actionGroup;
	}

	private ActionGroup<T> createActionGroup(JNActionGroup group) {
		ActionGroup<T> actionGroup = null;
		if (group != null) {
			String first = StringHelper.isNotEmptyWithTrim(group.firstActionStyle()) ? group.firstActionStyle() : firstActionStyle;
			String last = StringHelper.isNotEmptyWithTrim(group.lastActionStyle()) ? group.lastActionStyle() : lastActionStyle;
			String style = StringHelper.isNotEmptyWithTrim(group.actionStyle()) ? group.actionStyle() : actionStyle;
			actionGroup = new ActionButtonGroup<T>(secured, listener, first, last, style);
		
			if (StringHelper.isNotEmptyWithTrim(group.style()))
				actionGroup.setStyleName(group.style());
		}
	
		if (actionGroup == null) 
			actionGroup = new ActionButtonGroup<T>(secured, listener, firstActionStyle, lastActionStyle, actionStyle);

		actionGroup.setActionGroup(group);
		actionGroup.setSpacing(isSpacing());
		return actionGroup;
	}

	public boolean isShowSelectedAction() {
		return listener.isShowSelectedAction();
	}

	public void setShowSelectedAction(boolean showSelectedAction) {
		this.listener.setShowSelectedAction(showSelectedAction);
	}
}
