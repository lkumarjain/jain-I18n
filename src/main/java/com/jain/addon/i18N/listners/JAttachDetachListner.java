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
package com.jain.addon.i18N.listners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jain.addon.JNIComponent;
import com.jain.addon.JNIComponentInit;
import com.jain.addon.i18N.I18NHelper;
import com.jain.addon.i18N.component.I18NUI;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HasComponents.ComponentAttachEvent;
import com.vaadin.ui.HasComponents.ComponentAttachListener;
import com.vaadin.ui.HasComponents.ComponentDetachEvent;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.SingleComponentContainer;

/**
 * <code>JAttachDetachListner<code> is a listener for all the components to configure the i18N and Event handlers.
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings({ "serial", "deprecation" })
public class JAttachDetachListner implements ComponentDetachListener, ComponentAttachListener {
	private List<Component> components = new ArrayList<Component>();

	public void componentAttachedToContainer(ComponentAttachEvent event) {
		Component component = event.getAttachedComponent();
		initializeNAddRemoveListner(component, false);
	}

	public void initializeNAddRemoveListner(Component component, boolean remove) {
		if (components.contains(component)) 
			return;

		handleAddRemoveComponent(component, remove);

		if(component instanceof ComponentContainer) {
			ComponentContainer container = (ComponentContainer) component;
			container.addComponentAttachListener(this);
			container.addComponentDetachListener(this);
		} else if (component instanceof SingleComponentContainer) {
			SingleComponentContainer container = (SingleComponentContainer) component;
			container.addComponentAttachListener(this);
			container.addComponentDetachListener(this);
		}
		
		Boolean isPopup = false;
		if (component instanceof PopupView) {
			PopupView popupView = (PopupView) component;
			Component popupComponent = popupView.getContent().getPopupComponent();
			if (popupComponent != null) {
				isPopup = true;
				popupComponent.setParent(popupView);
				component = popupComponent;
			}
			
		}

		if(component instanceof HasComponents) {
			HasComponents container = (HasComponents) component;
			for (Component containerComponent : container) {
				initializeNAddRemoveListner(containerComponent, remove);
			}
		}
		
		if (isPopup) {
			component.setParent(null);
		}

		initializeComponent(component);
	}

	private void initializeComponent(Component component) {
		Method[]  methods = component.getClass().getMethods();
		for (Method method : methods) {
			JNIComponentInit componentInit = method.getAnnotation(JNIComponentInit.class);
			if(componentInit != null) {
				try {		
					method.invoke(component);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (component instanceof JNIComponent)
			((JNIComponent) component).init();
	}

	private void handleAddRemoveComponent(Component component, boolean remove) {
		if(remove) {
			components.remove(component);
			if (component.getUI() != null) {
				I18NHelper.deRegistor(component.getUI(), component);

				if (component.getUI() instanceof I18NUI) {
					((I18NUI)component.getUI()).getEventHandler().deRegistor(component);
				}
			} else 
				System.out.println("Component root is null :: " + component);
		} else {
			components.add(component);
			I18NHelper.register(component.getUI(), component);

			if (component.getUI() instanceof I18NUI) {
				((I18NUI)component.getUI()).getEventHandler().register(component);
			}
		}
	}

	public void componentDetachedFromContainer(ComponentDetachEvent event) {
		Component component = event.getDetachedComponent();
		initializeNAddRemoveListner(component, true);
	}

	/**
	 * Add this listener to a component
	 * @param parent
	 * @param component
	 */
	public static void addListenerToComponent (AbstractComponent parent, Component component) {
		Collection<?> collection = parent.getListeners(ComponentAttachEvent.class);
		for (Object object : collection) {
			if (object instanceof JAttachDetachListner)
				((JAttachDetachListner) object).initializeNAddRemoveListner(component, false);
		}

		System.out.println(collection);
	}

	/**
	 * Remove this listener from component
	 * @param parent
	 * @param component
	 */
	public static void removeListenerFromComponent (AbstractComponent parent, Component component) {
		Collection<?> collection = parent.getListeners(ComponentAttachEvent.class);
		for (Object object : collection) {
			if (object instanceof JAttachDetachListner)
				((JAttachDetachListner) object).initializeNAddRemoveListner(component, true);
		}

		System.out.println(collection);
	}
}
