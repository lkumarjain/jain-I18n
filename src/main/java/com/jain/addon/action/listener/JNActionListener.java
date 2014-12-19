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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jain.addon.StringHelper;
import com.jain.addon.action.JNAction;
import com.jain.addon.action.confirm.ConfirmWindow;
import com.jain.addon.action.confirm.JNConfirm;
import com.jain.addon.event.MethodExpression;
import com.vaadin.ui.UI;

/**
 * <code>JNActionListener<code> abstract action listener for all the actions
 * @author Lokesh Jain
 * @since December 5, 2012
 * @version 1.1.0
 * @param <T>
 *
 */
public abstract class JNActionListener <T> {
	private final T actionHandler;
	private Map<String, MethodExpression> actions;
	private Map<String, Object []> actionParams;
	private boolean showSelectedAction = false;

	/**
	 * @param actionHandler
	 * @param params
	 */
	public JNActionListener(T actionHandler, Object ... params) {
		this(true, actionHandler, params);
	}

	/**
	 * @param populate - Do we need to process annotations by default
	 * @param actionHandler - Object should have some methods annotated with {@link JNAction}
	 * @param params 
	 */
	public JNActionListener(boolean populate, T actionHandler, Object ... params) {
		this.actionHandler = actionHandler;
		this.actions = new HashMap <String, MethodExpression> ();
		this.actionParams = new HashMap<String, Object []>();

		if (populate)
			processActionHandler(params);
	}

	private void processActionHandler(Object ... params) {
		if (actionHandler != null) {
			Method[]  methods = actionHandler.getClass().getMethods();
			for (Method method : methods) {
				JNAction action = method.getAnnotation(JNAction.class);
				if(action != null) {
					addAction(action, method, params);
				}
			}
		}
	}

	/**
	 * Add an action with method and parameters
	 * @param action
	 * @param method
	 * @param params
	 * @return action Name
	 */
	public String addAction(JNAction action, Method method, Object... params) {
		String actionName = null;
		if (StringHelper.isNotEmptyWithTrim(action.name())) {
			actionName = action.name();
		} else {
			actionName = StringHelper.methodToPropertyName(method.getName());
		}
		MethodExpression expression = new MethodExpression(actionHandler, method);
		JNConfirm confirm = method.getAnnotation(JNConfirm.class);
		expression.setConfirm(confirm);
		actions.put(actionName, expression);
		processParameters (actionName, method, params);
		return actionName;
	}

	private void processParameters(String actionName, Method method, Object ... params) {
		if (params != null) {
			Class <?> [] paramTypes = method.getParameterTypes();
			if (paramTypes.length > 0) {
				Object [] actionParams = new Object [paramTypes.length];
				for (int i = 0; i < paramTypes.length; i ++) {
					Class <?> paramType = paramTypes [i];
					for (Object param : params) {
						if (param != null && paramType.isAssignableFrom(param.getClass())) {
							actionParams [i] = param;
							break;
						}
					}
				}
				this.actionParams.put(actionName, actionParams);
			}
		}
	}

	/**
	 * Add Action parameters
	 * @param action
	 * @param actionParams
	 */
	public void addActionParams (String action, Object ... actionParams) {
		this.actionParams.put(action, actionParams);
	}

	/**
	 * Remove action parameters
	 * @param action
	 */
	public void removeActionParams (String action) {
		this.actionParams.remove(action);
	}
	
	/**
	 * Method to Invoke Action
	 * @param actionName
	 * @param ui 
	 */
	protected void invokeAction(String actionName, UI ui) {
		MethodExpression action = actions.get(actionName);
		if (action != null) {
			if (action.getConfirm() != null) {
				ConfirmWindow window = new ConfirmWindow(action, this.actionParams.get(actionName));
				ui.addWindow(window);
			} else {
				action.invoke(this.actionParams.get(actionName));
			}
		}
	}

	public T getActionHandler() {
		return actionHandler;
	}

	public boolean isShowSelectedAction() {
		return showSelectedAction;
	}

	public void setShowSelectedAction(boolean showSelectedAction) {
		this.showSelectedAction = showSelectedAction;
	}
}
