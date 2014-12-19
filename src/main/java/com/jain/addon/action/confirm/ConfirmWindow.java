/* 
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
package com.jain.addon.action.confirm;

import com.jain.addon.JNIComponentInit;
import com.jain.addon.JNStyleConstants;
import com.jain.addon.StringHelper;
import com.jain.addon.action.ActionMenuBar;
import com.jain.addon.action.JNAction;
import com.jain.addon.event.MethodExpression;
import com.jain.addon.i18N.component.I18NWindow;
import com.jain.addon.resource.PropertyReader;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * <code>ConfirmWindow<code> is a window called for confirmation.
 * @author Lokesh Jain
 * @since Jan 01, 2013
 * @version 1.1.2
 */
@SuppressWarnings("serial")
public class ConfirmWindow extends I18NWindow  {
	private final MethodExpression action;
	private Object [] parameters;

	public ConfirmWindow(final MethodExpression action, Object ... parameters) {
		this.action = action;
		this.parameters = parameters;
		setCaption(action.getConfirm().title());
	}

	@JNIComponentInit
	public void init () {
		setModal(true);
		setWidth("25%");

		VerticalLayout layout = new VerticalLayout();
		setContent(layout);

		layout.setWidth("100%");
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setStyleName(JNStyleConstants.J_ALTERNATE_VIEW);

		JNConfirm confirm = action.getConfirm();
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSpacing(true);
		hLayout.setWidth("100%");
		findNAddIcon(confirm, hLayout);
		
		Label label = new Label(confirm.message());
		label.setContentMode(confirm.mode());
		hLayout.addComponent(label);
		hLayout.setExpandRatio(label, 2);
		layout.addComponent(hLayout);
		layout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		
		ActionMenuBar<ConfirmWindow> menuBar = new ActionMenuBar<ConfirmWindow>(null, this);
		layout.addComponent(menuBar);
		layout.setExpandRatio(menuBar, 2);
		layout.setComponentAlignment(menuBar, Alignment.MIDDLE_CENTER);
	}

	@JNAction (name = "confirm.window.confirm.action.name", actionGroup = "confirm.window.action.group.name", tabIndex = 10,
			icon = "confirm.window.confirm.action.icon")
	public void confirm() {
		action.invoke(parameters);
		getUI().removeWindow(this);
	}

	@JNAction (name = "confirm.window.cancel.action.name", actionGroup = "confirm.window.action.group.name", tabIndex = 11,
			icon = "confirm.window.cancel.action.icon")
	public void cancel() {
		getUI().removeWindow(this);
	}
	
	/**
	 * 
	 * @param confirm
	 * @param layout
	 */
	private void findNAddIcon(JNConfirm confirm, HorizontalLayout layout) {
		if (StringHelper.isNotEmptyWithTrim(confirm.icon())) {
			String iconPath = PropertyReader.instance().getProperty(confirm.icon());

			if (StringHelper.isNotEmptyWithTrim(iconPath)) {
				Label label = new Label();
				ThemeResource icon = new ThemeResource(iconPath);
				label.setIcon(icon);
				layout.addComponent(label);
				layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			}
		}
	}
}
