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

/**
 * Interface for context.
 * 
 * @author lofi
 */
public interface Context {

	/**
	 * Logger for info.
	 * 
	 * @param info
	 *            information to be written
	 */
	public void logInfo(String info);

	/**
	 * Set the project work directory, where we can rework on the structure of
	 * the Ant project.
	 * 
	 * @param projectWorkDirectory
	 *            work directory of the Ant project
	 */
	public void setProjectWorkDirectory(String projectWorkDirectory);

	/**
	 * Get the project work directory, where we can rework on the structure of
	 * the Ant project.
	 * 
	 * @return the work directory
	 */
	public String getProjectWorkDirectory();

	/**
	 * Set the Ant zip file project to be worked with.
	 * 
	 * @param zipFile
	 *            the zip
	 */
	public void setZipFile(String zipFile);

	/**
	 * Get the Ant zip file project.
	 * 
	 * @return the zip file with its complete directory
	 */
	public String getZipFile();

	/**
	 * Set the directory project to be worked with.
	 * 
	 * @param directory
	 *            the directory
	 */
	public void setDirectory(String directory);

	/**
	 * Get the directory project.
	 * 
	 * @return the directory with its complete directory
	 */
	public String getDirectory();

	/**
	 * Get the project name.
	 * 
	 * @return the name of the project
	 */
	public String getProjectName();

	/**
	 * Get the source name of the project.
	 * 
	 * @return the name of the project plus the source
	 */
	public String getProjectSourceName();

	/**
	 * Get the target name of the project.
	 * 
	 * @return the name of the project plus the target
	 */
	public String getProjectTargetName();
}
