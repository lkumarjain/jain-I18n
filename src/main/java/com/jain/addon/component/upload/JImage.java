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

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jain.addon.component.JStreamSource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;

/**
 * <code>JImage<code> is a Image custom component
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public class JImage extends CustomField<byte []> {
	private static final long serialVersionUID = 7490297043002025899L;
	private Embedded image;
	private HorizontalLayout layout;
	private JImageUpload uploader;

	public JImage() {
		this.uploader = new JImageUpload(this);
	}

	public void createImage() {
		if(image != null)
			layout.removeComponent(image);

		image = new Embedded();
		image.setWidth("100%");
		layout.addComponent(image, 0);
		layout.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
	}

	public Class<byte []> getType() {
		return byte [].class;
	}

	public void updateImage(final byte [] imageData, String fileName) {
		setValue(imageData);
		JStreamSource source = new JStreamSource(new ByteArrayInputStream(imageData));
		image.setSource(new StreamResource(source, fileName));
		image.markAsDirty();
	}

	protected Component initContent() {
		layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setWidth("100%");

		if (super.getInternalValue() != null) {
			createImage();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = "myfilename-" + df.format(new Date()) + ".png";
			JStreamSource source = new JStreamSource(new ByteArrayInputStream(super.getInternalValue()));
			image.setSource(new StreamResource(source, fileName));
			image.markAsDirty();
		} 

		if (!isReadOnly()) {
			layout.addComponent(uploader);
			layout.setComponentAlignment(uploader, Alignment.MIDDLE_CENTER);
		}
		return layout;
	}

	public String getInterruptionMessage() {
		return uploader.getInterruptionMessage();
	}

	public void setInterruptionMessage(String interruptionMessage) {
		uploader.setInterruptionMessage(interruptionMessage);
	}

	public long getMaxContentLength() {
		return uploader.getMaxContentLength();
	}

	public void setMaxContentLength(long maxContentLength) {
		uploader.setMaxContentLength(maxContentLength);
	}

	public String getUploadButtonCaption() {
		return uploader.getButtonCaption();
	}

	public void setUploadButtonCaption(String uploadButtonCaption) {
		uploader.setButtonCaption(uploadButtonCaption);
	}
}
