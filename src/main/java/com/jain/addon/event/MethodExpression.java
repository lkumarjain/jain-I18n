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
package com.jain.addon.event;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.jain.addon.action.confirm.JNConfirm;

/**
 * <pre> 
 * <code>MethodExpression<code> created for the observer method
 * @param instance - object instance where this observer method is available
 * @param method - The observer method.
 * </pre>
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public final class MethodExpression implements Serializable {
	private final Object instance;
	private final Method method;
	private JNConfirm confirm;

	public MethodExpression(Object instance, Method method) {
		this.instance = instance;
		this.method = method;
	}

	public Object getInstance() {
		return instance;
	}

	public Method getMethod() {
		return method;
	}

	public JNConfirm getConfirm() {
		return confirm;
	}

	public void setConfirm(JNConfirm confirm) {
		this.confirm = confirm;
	}

	public Object invoke(Object ... parameters) {
		try {
			return this.method.invoke(instance, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
