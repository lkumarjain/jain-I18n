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
package com.jain.addon.i18N;

import com.vaadin.ui.Component;

/**
 * <code>I18NListener<code> is a listener for the 
 * application component to listen on the locale change.
 * So that you can provide some business changes based on locale change. 
 * @author Lokesh Jain
 * @since Aug 27, 2012
 * @version 1.0.0
 */
public interface I18NListener extends Component {
	public void localeChanged (I18NChangeEvent event);
}
