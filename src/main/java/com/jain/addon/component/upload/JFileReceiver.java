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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.ui.Upload.Receiver;

/**
 * <code>JFileReceiver<code> is a default receiver implementation for the file receiving from the {@link JImageUpload} component 
 * @author Lokesh Jain
 * @since Aug 28, 2012
 * @version 1.0.0
 */
public class JFileReceiver implements Receiver {
	private static final long serialVersionUID = 5756898625595137143L;
	private String fileName;
	private String mimeType;
	private String filePath;
	private OutputStream stream;

	public JFileReceiver() {
	}
	
	public JFileReceiver(OutputStream stream) {
		this.stream = stream;
	}

	public OutputStream receiveUpload(String filename, String mimeType) {
		this.fileName = filename;
		this.mimeType = mimeType;

		if (stream == null) {
			try {
				File tmpDir = new File(System.getProperty("java.io.tmpdir") + "/" + "JUpload$tmp");
				tmpDir.mkdir();
				this.filePath = tmpDir.getCanonicalPath() + "/" + fileName.replace(" ", "_");
				stream = new FileOutputStream(new File(this.filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return stream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream stream) {
		this.stream = stream;
	}
}
