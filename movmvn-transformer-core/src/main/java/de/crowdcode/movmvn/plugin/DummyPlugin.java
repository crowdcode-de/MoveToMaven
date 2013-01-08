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
package de.crowdcode.movmvn.plugin;

import java.util.logging.Logger;

import de.crowdcode.movmvn.core.Context;
import de.crowdcode.movmvn.core.Plugin;

/**
 * Dummy Plugin.
 * 
 * @author lofi
 */
public class DummyPlugin implements Plugin {

	private static final Logger log = Logger.getLogger(DummyPlugin.class
			.getName());

	@Override
	public void execute(Context context) {
		log.info("Execute Dummy Plugin...");
	}

	@Override
	public String getName() {
		return "DummyPlugin";
	}

	@Override
	public int getExecutionOrderedNumber() {
		return 100;
	}
}
