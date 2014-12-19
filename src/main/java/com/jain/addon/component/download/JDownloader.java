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
package com.jain.addon.component.download;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import com.jain.addon.component.JStreamSource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.UI;

/**
 * <code>JDownloader<code> is a helper class to download files
 * @author Lokesh Jain
 * @since Aug 29, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JDownloader implements Serializable {
	private String fileName;
	private UI ui;
	private JStreamSource source;
	
	/**
	 * Create a downloader instance using filePath, fileName and {@link UI}
	 * @param filePath
	 * @param fileName
	 * @param ui
	 * @throws FileNotFoundException 
	 */
	public JDownloader(String filePath, String fileName, UI ui) throws FileNotFoundException {
		this.source = new JStreamSource(filePath);
		this.fileName = fileName;
		this.ui = ui;
	}

	/**
	 * Create a downloader instance using fileContent, fileName and {@link UI}
	 * @param fileContent
	 * @param fileName
	 * @param ui
	 */
	public JDownloader(byte [] fileContent, String fileName, UI ui) {
		this.source = new JStreamSource(fileContent);
		this.fileName = fileName;
		this.ui = ui;
	}
	
	/**
	 * Create a downloader instance using stream, fileName and {@link UI}
	 * @param fileContent
	 * @param fileName
	 * @param ui
	 */
	public JDownloader(InputStream stream, String fileName, UI ui) {
		this.source = new JStreamSource(stream);
		this.fileName = fileName;
		this.ui = ui;
	}

	/**
	 * Download actual file
	 */
	public void download() {
		StreamResource resource = new StreamResource(source, fileName);
		resource.getStream().setParameter("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		resource.setMIMEType ("application/octet-stream");
		resource.setCacheTime (0);
		FileDownloader downloader = new FileDownloader(resource); 
		downloader.extend(ui);
	}
}
