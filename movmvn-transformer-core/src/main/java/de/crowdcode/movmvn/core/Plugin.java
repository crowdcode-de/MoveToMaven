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
 * Interface for plugin.
 * 
 * @author lofi
 */
public interface Plugin {

	/**
	 * Execute the plugin.
	 * 
	 * @param context
	 *            context information for the plugin
	 */
	public void execute(final Context context);

	/**
	 * Execute the plugin.
	 * 
	 * @param context
	 *            context information for the plugin
	 * @param isProjectGroup
	 *            projectGroup means that there are many projects within the
	 *            directory
	 */
	public void execute(final Context context, final boolean isProjectGroup);

	/**
	 * Get the name of the plugin.
	 * 
	 * @return the name of the plugin
	 */
	public String getName();

	/**
	 * Get an integer number for ordering the execution of the plugin.
	 * 
	 * @return an integer as an ordering number
	 */
	public int getExecutionOrderedNumber();
}
