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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.StringHelper;
import com.jain.addon.action.listener.JNCommandListener;
import com.jain.addon.authentication.JNILoginListner;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.resource.PropertyReader;
import com.jain.addon.security.JNISecured;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * <code>ActionBar<code> is a horizontal segment component for actions.<br/>
 * @author Lokesh Jain
 * @since November 25, 2012
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class ActionMenuBar <T> extends HorizontalLayout implements JNILoginListner {
	private List<JNAction> actions;
	private Map<String, JNActionGroup> actionGroupByName;
	private Map<JNAction, String> actionsToName;
	private MenuBar menuBar;
	private final String firstActionStyle;
	private final String lastActionStyle;
	private final String actionStyle;
	private final JNISecured secured; 
	private final JNCommandListener<T> listener;
	private boolean initialized = false;

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 */
	public ActionMenuBar(JNISecured secured, T actionHandler) {
		this(secured, actionHandler, JNStyleConstants.J_FIRST_ACTION, JNStyleConstants.J_LAST_ACTION, JNStyleConstants.J_ACTION);
	}

	/**
	 * Create a segment instance having {@link Button} style for all the buttons
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param actionStyle
	 */
	public ActionMenuBar(JNISecured secured, T actionHandler, String actionStyle) {
		this(secured, actionHandler, actionStyle, actionStyle, actionStyle);
	}

	/**
	 * Create a segment instance having first {@link Button} and last {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionMenuBar(JNISecured secured, T actionHandler, String firstActionStyle, String lastActionStyle) {
		this(secured, actionHandler, firstActionStyle, lastActionStyle, null);
	}

	/**
	 * Create a segment instance having first {@link Button}, last {@link Button} and {@link Button} styles
	 * @param secured -- {@link JNISecured}
	 * @param actionHandler
	 * @param firstActionStyle
	 * @param lastActionStyle
	 */
	public ActionMenuBar(JNISecured secured, T actionHandler, String firstActionStyle, String lastActionStyle, String actionStyle) {
		this.firstActionStyle = firstActionStyle;
		this.lastActionStyle = lastActionStyle;
		this.actionStyle = actionStyle;
		this.secured = secured;
		this.menuBar = new MenuBar();
		this.listener = new JNCommandListener<T>(menuBar, false, actionHandler); 
		this.actions = new ArrayList<JNAction> ();
		this.actionsToName = new HashMap<JNAction, String>(); 
		this.actionGroupByName = new HashMap<String, JNActionGroup> ();
		setStyleName(JNStyleConstants.J_ACTION_BAR);
	}

	/**
	 * Method to initialize component
	 */
	@JNIComponentInit
	public void initActionMenuBar() {
		if (!initialized) {
			findActions ();
			Map<String, MenuItem> menuItemByGroupName = new HashMap<String, MenuItem>();
			
			String first = firstActionStyle;
			String last = lastActionStyle;
			String style = actionStyle;
			
			int i = 0;
			MenuItem lastItem = null;
			for (JNAction action : actions) {
				MenuItem parentItem = null;
				if (StringHelper.isNotEmptyWithTrim(action.actionGroup())) {
					parentItem = menuItemByGroupName.get(action.actionGroup());

					if (parentItem == null) {
						JNActionGroup actionGroup = actionGroupByName.get(action.actionGroup());
						if (actionGroup != null) {
							first = StringHelper.isNotEmptyWithTrim(actionGroup.firstActionStyle()) ? actionGroup.firstActionStyle() : firstActionStyle;
							last = StringHelper.isNotEmptyWithTrim(actionGroup.lastActionStyle()) ? actionGroup.lastActionStyle() : lastActionStyle;
							style = StringHelper.isNotEmptyWithTrim(actionGroup.actionStyle()) ? actionGroup.actionStyle() : actionStyle;
							parentItem = createMenuItem (actionGroup, menuItemByGroupName);
							menuItemByGroupName.put(action.actionGroup(), parentItem);
						}
					}
				}
				
				lastItem = processAction(parentItem, first, style, i, action);
				i++;
			}

			if (lastItem != null) {
				lastItem.setStyleName(last);
			}

			initialized = true;
			addComponent(menuBar);
		} else {
			throw new IllegalArgumentException ("Please add action before adding component into container");
		}
	}

	/**
	 * @param actionGroup
	 * @param menuItemByGroupName
	 * @return
	 */
	private MenuItem createMenuItem(JNActionGroup actionGroup, Map<String, MenuItem> menuItemByGroupName) {
		MenuItem menuItem = null;
		MenuItem parentItem = null;
		if (StringHelper.isNotEmptyWithTrim(actionGroup.parent())) {
			parentItem = menuItemByGroupName.get(actionGroup.parent());

			if (parentItem == null) {
				JNActionGroup parentActionGroup = actionGroupByName.get(actionGroup.parent());
				if (parentActionGroup != null) {
					parentItem = createMenuItem(parentActionGroup, menuItemByGroupName);
					menuItemByGroupName.put(parentActionGroup.name(), parentItem);
				}
			}
		}

		menuItem = parentItem == null ? menuBar.addItem(actionGroup.name(), null) : parentItem.addItem(actionGroup.name(), null);
		findNAddIcon(actionGroup.icon(), menuItem);

		if (StringHelper.isNotEmptyWithTrim(actionGroup.description()))
			menuItem.setDescription(actionGroup.description());

		if (StringHelper.isNotEmptyWithTrim(actionGroup.style()))
			menuItem.setStyleName(actionGroup.style());
		return menuItem;
	}

	/**
	 * @param parentItem
	 * @param first
	 * @param style
	 * @param i
	 * @param action
	 * @return
	 */
	private MenuItem processAction(MenuItem parentItem, String first, String style, int i, JNAction action) {
		MenuItem lastItem;
		lastItem = parentItem == null ? menuBar.addItem(actionsToName.get(action), listener) : parentItem.addItem(actionsToName.get(action), listener);

		findNAddIcon(action.icon(), lastItem);
		lastItem.setVisible(validatePermission (action));

		if (StringHelper.isNotEmptyWithTrim(action.description()))
			lastItem.setDescription(action.description());
		else
			lastItem.setDescription(actionsToName.get(action));

		if (i == 0)
			lastItem.setStyleName(first);
		else 
			lastItem.setStyleName(style);

		if (action.separator() && parentItem != null) {
			parentItem.addSeparator();
		}
		return lastItem;
	}
	

	private void findNAddIcon(String actionIcon, MenuItem action) {
		if (StringHelper.isNotEmptyWithTrim(actionIcon)) {
			String iconPath = PropertyReader.instance().getProperty(actionIcon);

			if (StringHelper.isNotEmptyWithTrim(iconPath)) {
				ThemeResource icon = new ThemeResource(iconPath);
				action.setIcon(icon);
			}
		}
	}

	
	private void findActions() {
		if (listener.getActionHandler() != null) {
			JNActionGroups groups = listener.getActionHandler().getClass().getAnnotation(JNActionGroups.class);
			if (groups != null) {
				for (JNActionGroup group : groups.actionGroups()) {
					if(group != null && StringHelper.isNotEmptyWithTrim(group.name())) {
						actionGroupByName.put(group.name(), group);
					}
				}
			}

			JNActionGroup group = listener.getActionHandler().getClass().getAnnotation(JNActionGroup.class);
			if(group != null && StringHelper.isNotEmptyWithTrim(group.name())) {
				actionGroupByName.put(group.name(), group);
			}

			Method[]  methods = listener.getActionHandler().getClass().getMethods();
			for (Method method : methods) {
				JNAction action = method.getAnnotation(JNAction.class);
				if(action != null) {
					int position = findPosition(actions, action);
					actions.add(position, action);

					String actionName = listener.addAction(action, method);
					actionsToName.put(action, actionName);
				}
			}
		}
	}

	private int findPosition(List<JNAction> actions, JNAction action) {
		int i = 0;
		for (JNAction act : actions) {
			if (act.tabIndex() < action.tabIndex())
				i ++;
		}
		return i;
	}

	public boolean isShowSelectedAction() {
		return listener.isShowSelectedAction();
	}

	public void setShowSelectedAction(boolean showSelectedAction) {
		this.listener.setShowSelectedAction(showSelectedAction);
	}

	protected boolean validatePermission(JNAction action) {
		if (secured != null) {
			return secured.hasPermission(action.permission());
		}
		return true;
	}

	/**
	 * Reinitialize a Action Bar by updating resources {@link JNAction} visibility after login.<br/> 
	 */
	public void onLogin() {
		if (actions != null) {
			for (JNAction action : actions) {
				for (Iterator<MenuItem> iterator = menuBar.getItems().listIterator(); iterator.hasNext();) {
					MenuItem item = iterator.next();
					if (actionsToName.get(action).equalsIgnoreCase(I18NHelper.getKey(menuBar, item))) {
						item.setVisible(validatePermission (action));
					}
				}
			}
		}
		markAsDirty();
	}
}
