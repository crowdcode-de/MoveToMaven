package de.crowdcode.movmvn.core;

import com.google.inject.AbstractModule;

/**
 * Guice module for transformer.
 * 
 * @author lofi
 */
public class MovToMvnTransformerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Context.class).to(ContextImpl.class);
	}
}