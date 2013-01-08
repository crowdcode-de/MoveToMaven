/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.crowdcode.movmvn.core;

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of an object context.
 * 
 * @author lofi
 */
public class ContextImpl implements Context {

	private static final Logger log = Logger.getLogger(ContextImpl.class
			.getName());
	private String projectWorkDirectory;
	private String zipFile;
	private String directory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logInfo(final String info) {
		log.info(info);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProjectWorkDirectory(final String projectWorkDirectory) {
		this.projectWorkDirectory = projectWorkDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectWorkDirectory() {
		return this.projectWorkDirectory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setZipFile(final String zipFile) {
		this.zipFile = zipFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getZipFile() {
		return this.zipFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectName() {
		if (zipFile != null && !zipFile.equals("")) {
			String nameWithoutZip = StringUtils
					.substringBefore(zipFile, ".zip");
			String nameResult = StringUtils.substringAfterLast(nameWithoutZip,
					"/");
			if (nameResult.equals("")) {
				// We have no "/" instead "\\"
				nameResult = StringUtils.substringAfterLast(nameWithoutZip,
						"\\");
			}
			return nameResult;
		} else if (directory != null && !directory.equals("")) {
			String nameResult = StringUtils.substringAfterLast(directory, "/");
			return nameResult;
		} else {
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectSourceName() {
		String resultName = projectWorkDirectory.concat("/").concat(
				getProjectName());
		return resultName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProjectTargetName() {
		String resultName = projectWorkDirectory.concat("/target/").concat(
				getProjectName());
		return resultName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDirectory(final String directory) {
		this.directory = directory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDirectory() {
		return this.directory;
	}
}
