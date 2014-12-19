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
package com.jain.addon.component.upload;

import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload.ProgressListener;

/**
 * <code>JProgressIndicator<code> default progress indicator for the {@link JImageUpload}
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public class JProgressIndicator extends ProgressBar implements ProgressListener {
	private static final long serialVersionUID = 5756898625595137143L;
	private long readBytes;
	private long contentLength;
	
	public JProgressIndicator() {
		setWidth("100%");
		setValue(0f);
	}

	public void updateProgress(long readBytes, long contentLength) {
		this.readBytes = readBytes;
		this.contentLength = contentLength;
		setValue(new Float(readBytes / (float) contentLength));
	}

	public long getReadBytes() {
		return readBytes;
	}

	public void setReadBytes(long readBytes) {
		this.readBytes = readBytes;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
}
