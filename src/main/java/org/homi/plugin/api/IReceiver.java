package org.homi.plugin.api;

/**
 * 
 * @author Nicolas Hanout
 *
 * @param <T>
 */
public interface IReceiver<T> {
	/**
	 * 
	 * @param args
	 * @return
	 */
	T doAction(Object ...args);
}
