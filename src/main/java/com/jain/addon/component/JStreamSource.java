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
package com.jain.addon.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

/**
 * <code>JStreamSource<code> is a default implementation for {@link StreamResource}.
 * @author Lokesh Jain
 * @since Aug 29, 2012
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class JStreamSource implements StreamSource {
	private final InputStream stream;

	/**
	 * Create a stream resource instance using absolute file path
	 * @param filePath
	 * @throws FileNotFoundException 
	 */
	public JStreamSource(String filePath) throws FileNotFoundException {
		this.stream = new FileInputStream(new File(filePath));
	}

	/**
	 * Create a stream resource instance using absolute file content byte array
	 * @param fileContent
	 */
	public JStreamSource(byte [] fileContent) {
		this.stream = new ByteArrayInputStream(fileContent);
	}

	/**
	 * Create a stream resource instance using stream
	 * @param fileContent
	 */
	public JStreamSource(InputStream stream) {
		this.stream = stream;
	}

	public InputStream getStream() {
		return this.stream;
	}
}
