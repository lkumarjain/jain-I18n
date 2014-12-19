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

import java.io.ByteArrayOutputStream;

import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;

/**
 * <code>JImageUpload<code> default Image uploader
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JImageUpload extends JUploader {
	private ByteArrayOutputStream stream;
	private JImage jImage;

	public JImageUpload(JImage jImage) {
		super ();
		this.jImage = jImage;
		stream = new ByteArrayOutputStream(10240);
		stream.reset();
		super.setStream(stream);
	}

	public Object getValue() {
		return stream.toByteArray();
	}

	public ByteArrayOutputStream getStream() {
		return stream;
	}

	public void setStream(ByteArrayOutputStream stream) {
		this.stream = stream;
	}

	public void uploadStarted(StartedEvent event) {
		super.uploadStarted(event);
		long l = event.getContentLength();
		if(l < getMaxContentLength()) {
			stream.reset();
			jImage.createImage();
		}
	}

	public void uploadSucceeded(SucceededEvent event) {
		jImage.updateImage(stream.toByteArray(), event.getFilename());
		super.uploadSucceeded(event);
	}
}
