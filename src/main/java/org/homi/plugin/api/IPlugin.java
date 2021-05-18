package org.homi.plugin.api;

/**
 * 
 * @author Nicolas Hanout
 *
 * @since 0.0.1
 */
public interface IPlugin {
	/**
	 * 
	 * @return
	 */
	public Class<? extends IPlugin> getKind();
}
