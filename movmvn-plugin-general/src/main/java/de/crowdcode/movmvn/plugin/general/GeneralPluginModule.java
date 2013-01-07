package de.crowdcode.movmvn.plugin.general;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.crowdcode.movmvn.core.Plugin;

/**
 * Guice module for General plugin.
 * 
 * @author lofi
 */
public class GeneralPluginModule extends AbstractModule {

	protected Multibinder<Plugin> plugin;

	@Override
	protected void configure() {
		plugin = Multibinder.newSetBinder(binder(), Plugin.class);
		plugin.addBinding().to(GeneralPlugin.class);
	}
}
